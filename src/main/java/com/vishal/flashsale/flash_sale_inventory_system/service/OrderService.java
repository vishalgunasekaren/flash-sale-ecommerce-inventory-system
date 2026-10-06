package com.vishal.flashsale.flash_sale_inventory_system.service;
import com.vishal.flashsale.flash_sale_inventory_system.entity.*;
import com.vishal.flashsale.flash_sale_inventory_system.exception.*;
import com.vishal.flashsale.flash_sale_inventory_system.dto.OrderRequest;
import com.vishal.flashsale.flash_sale_inventory_system.dto.OrderResponse;
import com.vishal.flashsale.flash_sale_inventory_system.enums.OrderStatus;
import com.vishal.flashsale.flash_sale_inventory_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final FlashSaleRepository flashSaleRepository;
    private final OrderItemRepository orderItemRepository;

    private final Clock clock;


    @Transactional
    public OrderResponse placeOrder(OrderRequest request){

        // 1. Validate user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        //2. A product may appear only once; otherwise each line would be checked
        // independently and could bypass the per-user purchase limit.
        // Check if the use send the same product twice
        Set<Long> productIds = new HashSet<>();
        for (OrderRequest.ItemsRequest item : request.getItems()) {
            if (!productIds.add(item.getProductId())) {
                throw new IllegalArgumentException(
                        "Each product can appear only once in an order request");
            }
        }

        // check if flash sale exist
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> pendingItems = new ArrayList<>();


        // 3. Validate + reserve stock for every line, branching on flash sale vs normal
        // check if the flashsale exists or not
        for(OrderRequest.ItemsRequest item : request.getItems()) {
            Long productId = item.getProductId();
            int quantityGivenByUser = item.getQuantity();
            LocalDateTime now = LocalDateTime.now(clock);

            Optional<FlashSale> activeSale  = flashSaleRepository.findActiveSale(productId, now);

            BigDecimal itemTotal;
            OrderItem orderItem;

            if(activeSale.isPresent()){
                // ---- Flash-sale path ----
                FlashSale fs = activeSale.get();

                // Check if the user exceed the limit or not for per order
                Long quantityBoughtByUser = orderItemRepository.countBoughtByUser(request.getUserId(), fs.getId(), OrderStatus.CANCELLED);

                Long totalBought = quantityBoughtByUser + quantityGivenByUser;

                if(totalBought > fs.getPerUserLimit()){
                    throw new LimitExceedException("user limit exceed for the this flashsale product");

                }

                // Atomic conditional decrement — avoids overselling under concurrency
                // A flash-sale order consumes physical product stock as well as the
                // sale allocation. Both conditional updates participate in this
                // transaction, so a failure in either one rolls back the other.
                int updatedProductRows = productRepository.stockUpdate(productId, quantityGivenByUser);

                if (updatedProductRows == 0) {
                    throw new OutOfStockException("Insufficient stock for product " + productId);
                }

                int updatedRows = flashSaleRepository.stockUpdate(fs.getId(), quantityGivenByUser);

                if(updatedRows == 0){
                    throw new OutOfStockException("Insufficient flash-sale stock");
                }

                // calculate total amount
                itemTotal = fs.getDiscountPrice().multiply(BigDecimal.valueOf(quantityGivenByUser));

                orderItem = OrderItem.builder()
                        .product(fs.getProduct())
                        .flashSale(fs)
                        .quantity(quantityGivenByUser)
                        .price(fs.getDiscountPrice())
                        .build();

            }
            else{
                // ---- Normal-price path ----

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException("Product not found"));

                int updatedRows = productRepository.stockUpdate(productId, quantityGivenByUser);

                if(updatedRows == 0){
                    throw new OutOfStockException("Insufficient stock for product " + productId);
                }

                itemTotal = product.getPrice().multiply(BigDecimal.valueOf(quantityGivenByUser));

                orderItem = OrderItem.builder()
                        .product(product)
                        .flashSale(null)
                        .quantity(quantityGivenByUser)
                        .price(product.getPrice())
                        .build();
            }

            totalAmount = totalAmount.add(itemTotal);
            pendingItems.add(orderItem);


        }
        Order order = Order.builder()
                .user(user)
                .totalAmount(totalAmount)
                .status(OrderStatus.CONFIRMED)
                .createdAt(LocalDateTime.now(clock))
                .build();

        order = orderRepository.save(order);


        for(OrderItem oi : pendingItems){
            oi.setOrder(order);
            orderItemRepository.save(oi);
        }

        return mapToResponse(order,pendingItems);


    }


    private OrderResponse mapToResponse(Order order, List<OrderItem> items){

        List<OrderResponse.ItemResponse> itemResponses = items.stream()
                .map(oi -> OrderResponse.ItemResponse.builder()
                        .productId(oi.getProduct().getId())
                        .productName(oi.getProduct().getName())
                        .quantity(oi.getQuantity())
                        .price(oi.getPrice())
                        .build())
                .toList();


        return OrderResponse.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .items(itemResponses)
                .build();
    }

}
