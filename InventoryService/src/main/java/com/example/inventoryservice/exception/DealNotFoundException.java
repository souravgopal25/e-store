package com.example.inventoryservice.exception;

public class DealNotFoundException extends RuntimeException {
    public DealNotFoundException(Long dealId) {
        super("Deal Not found with dealId" + dealId);
    }
}
