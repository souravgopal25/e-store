package com.example.inventoryservice.exception;

public class InventoryFoundException extends RuntimeException {
    public InventoryFoundException(Long id) {
        super("Inventory Already Exists with Id: " + id);
    }
}
