package com.example.inventoryservice.enums;

public enum DealState {
    DRAFT("DRAFT"),
    ACTIVELY_RAISING("ACTIVELY_RAISING"),
    SETTLEMENT_PENDING("SETTLEMENT_PENDING"),
    SETTLEMENT_DONE("SETTLEMENT_DONE"),
    COLLECTIONS_DUE("COLLECTIONS_DUE"),
    DISBURSAL_PENDING("DISBURSAL_PENDING"),
    DELETED("DELETED");

    private final String value;

    DealState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
