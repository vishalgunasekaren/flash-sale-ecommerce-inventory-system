package com.vishal.flashsale.flash_sale_inventory_system.service;

import com.vishal.flashsale.flash_sale_inventory_system.dto.FlashSaleRequest;
import com.vishal.flashsale.flash_sale_inventory_system.dto.FlashSaleResponse;
import com.vishal.flashsale.flash_sale_inventory_system.entity.FlashSale;
import com.vishal.flashsale.flash_sale_inventory_system.entity.Product;
import com.vishal.flashsale.flash_sale_inventory_system.exception.ProductNotFoundException;
import com.vishal.flashsale.flash_sale_inventory_system.repository.FlashSaleRepository;
import com.vishal.flashsale.flash_sale_inventory_system.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlashSaleService {

    private final FlashSaleRepository flashSaleRepository;
    private final ProductRepository productRepository;

    public FlashSaleResponse createFlashSale(FlashSaleRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (!request.startTime().isBefore(request.endTime())) {
            throw new IllegalArgumentException("startTime must be before endTime");
        }

        if (request.discountPrice().compareTo(product.getPrice()) >= 0) {
            throw new IllegalArgumentException("discountPrice must be less than the product's regular price");
        }

        if (request.stockLimit() > product.getStock()) {
            throw new IllegalArgumentException("Flash-sale stock cannot exceed available product stock");
        }

        FlashSale flashSale = FlashSale.builder()
                .product(product)
                .discountPrice(request.discountPrice())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .stockLimit(request.stockLimit())
                .perUserLimit(request.perUserLimit())
                .build();

        FlashSale saved = flashSaleRepository.save(flashSale);
        return toResponse(saved);
    }

    private FlashSaleResponse toResponse(FlashSale fs) {
        return new FlashSaleResponse(
                fs.getId(), fs.getProduct().getId(), fs.getDiscountPrice(),
                fs.getStartTime(), fs.getEndTime(), fs.getStockLimit(), fs.getPerUserLimit()
        );

    }
}
