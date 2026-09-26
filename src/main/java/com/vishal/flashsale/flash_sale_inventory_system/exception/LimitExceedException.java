package com.vishal.flashsale.flash_sale_inventory_system.exception;

public class LimitExceedException extends RuntimeException {
    public LimitExceedException(String message) {
        super(message);
    }
}
