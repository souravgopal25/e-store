package com.example.inventoryservice.exception;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(Long id) {
        super("Inventory Not found with Id: " + id);
    }
}
