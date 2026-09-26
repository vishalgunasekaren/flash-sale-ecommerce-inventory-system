package com.vishal.flashsale.flash_sale_inventory_system.controller;


import com.vishal.flashsale.flash_sale_inventory_system.dto.OrderRequest;
import com.vishal.flashsale.flash_sale_inventory_system.dto.OrderResponse;
import com.vishal.flashsale.flash_sale_inventory_system.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

   public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest request){
       OrderResponse response = orderService.placeOrder(request);

       return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }
}
