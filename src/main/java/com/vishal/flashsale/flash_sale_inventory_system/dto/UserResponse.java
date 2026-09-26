package com.vishal.flashsale.flash_sale_inventory_system.dto;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String role,
        LocalDateTime createdAt
) {}
