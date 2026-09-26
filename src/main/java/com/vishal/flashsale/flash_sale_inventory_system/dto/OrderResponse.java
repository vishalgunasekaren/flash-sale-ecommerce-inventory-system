package com.vishal.flashsale.flash_sale_inventory_system.dto;

import com.vishal.flashsale.flash_sale_inventory_system.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<ItemResponse> items;

    @Data @Builder
    public static class ItemResponse {
        private Long productId;
        private String productName;
        private int quantity;
        private BigDecimal price;
        private BigDecimal subtotal;
        private Long flashSaleId; // nullable
    }
}