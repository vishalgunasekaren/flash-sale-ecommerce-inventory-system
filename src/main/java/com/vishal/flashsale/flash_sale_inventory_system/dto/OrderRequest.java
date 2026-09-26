package com.vishal.flashsale.flash_sale_inventory_system.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {

    @NotNull
    @Positive
    private Long userId;

    @NotEmpty(message = "Atleast one item is required")
    @Valid
    List<ItemsRequest> items;

@Getter
@Setter
public static class ItemsRequest {

    @NotNull
    @Positive
    private Long productId;

    @Positive
    @Min(1)
    private int quantity;
 }
}
