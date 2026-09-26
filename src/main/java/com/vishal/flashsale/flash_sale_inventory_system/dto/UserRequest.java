package com.vishal.flashsale.flash_sale_inventory_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank String firstName,
        String lastName,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8) String passward

) {
}
