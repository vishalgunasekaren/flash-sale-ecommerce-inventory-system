package com.vishal.flashsale.flash_sale_inventory_system.controller;

import com.vishal.flashsale.flash_sale_inventory_system.dto.ProductRequest;
import com.vishal.flashsale.flash_sale_inventory_system.dto.ProductResponse;
import com.vishal.flashsale.flash_sale_inventory_system.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }
}
