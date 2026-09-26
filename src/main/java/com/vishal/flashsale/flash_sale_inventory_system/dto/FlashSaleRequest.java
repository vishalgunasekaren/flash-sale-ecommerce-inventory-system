package com.vishal.flashsale.flash_sale_inventory_system.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlashSaleRequest(
        @NotNull Long productId,
        @NotNull @Positive BigDecimal discountPrice,
        @NotNull @Future LocalDateTime startTime,
        @NotNull @Future LocalDateTime endTime,
        @NotNull @Min(1) Integer stockLimit,
        @NotNull @Min(1) Integer perUserLimit
) {
}
