package com.vishal.flashsale.flash_sale_inventory_system.service;

import com.vishal.flashsale.flash_sale_inventory_system.dto.ProductRequest;
import com.vishal.flashsale.flash_sale_inventory_system.dto.ProductResponse;
import com.vishal.flashsale.flash_sale_inventory_system.entity.Product;
import com.vishal.flashsale.flash_sale_inventory_system.entity.User;
import com.vishal.flashsale.flash_sale_inventory_system.exception.UserNotFoundException;
import com.vishal.flashsale.flash_sale_inventory_system.repository.ProductRepository;
import com.vishal.flashsale.flash_sale_inventory_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductResponse createProduct(ProductRequest request){

        User seller = userRepository.findById(request.sellerId())
                .orElseThrow(() -> new UserNotFoundException("Seller not found"));

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .seller(seller)
                .build();

        Product saved = productRepository.save(product);
        return toResponse(saved);

    }

    private ProductResponse toResponse(Product p){
        return new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice(),
                p.getStock(), p.getSeller().getId(), p.getCreatedAt()
        );
    }
}
