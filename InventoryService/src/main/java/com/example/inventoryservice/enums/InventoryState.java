package com.example.inventoryservice.enums;

public enum InventoryState {
    UNVERIFIED("UNVERIFIED"),
    VERIFIED("VERIFIED"),
    REJECTED("REJECTED"),
    UNLISTED("UNLISTED"),
    LISTED("LISTED"),
    DELETED("DELETED"),
    EXPIRED("EXPIRED");
    public final String status;

    InventoryState(String s) {
        status = s;
    }
}
