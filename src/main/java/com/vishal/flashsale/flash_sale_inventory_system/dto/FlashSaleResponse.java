package com.vishal.flashsale.flash_sale_inventory_system.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlashSaleResponse(
        Long id,
        Long productId,
        BigDecimal discountPrice,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Integer stockLimit,
        Integer perUserLimit
) {
}
