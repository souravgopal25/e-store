package com.example.inventoryservice.enums;

public enum DiscountedState {
    UNDISCOUNTED("UNDISCOUNTED"),
    PARTIALLY_DISCOUNTED("PARTIALLY_DISCOUNTED"),
    DISCOUNTED("DISCOUNTED");

    private final String value;

    DiscountedState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
