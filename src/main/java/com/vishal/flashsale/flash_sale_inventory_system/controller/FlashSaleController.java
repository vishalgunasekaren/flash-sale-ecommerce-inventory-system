package com.vishal.flashsale.flash_sale_inventory_system.controller;

import com.vishal.flashsale.flash_sale_inventory_system.dto.FlashSaleRequest;
import com.vishal.flashsale.flash_sale_inventory_system.dto.FlashSaleResponse;
import com.vishal.flashsale.flash_sale_inventory_system.service.FlashSaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flashsale")
@RequiredArgsConstructor
public class FlashSaleController {

    private final FlashSaleService flashSaleService;

    public ResponseEntity<FlashSaleResponse> createFlashSale(@Valid @RequestBody FlashSaleRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(flashSaleService.createFlashSale(request));
    }
}
