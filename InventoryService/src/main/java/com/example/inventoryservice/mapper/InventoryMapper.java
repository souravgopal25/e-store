package com.example.inventoryservice.mapper;

import com.example.inventoryService.InventoryDTO;
import com.example.inventoryservice.entity.Inventory;
import com.example.inventoryservice.enums.DiscountedState;
import com.example.inventoryservice.enums.InventoryState;

import java.math.BigDecimal;

import static com.example.inventoryservice.mapper.TimeMapper.getLocalDateTimeFromTimeStamp;
import static com.example.inventoryservice.mapper.TimeMapper.getTimeStampFromLocalDateTime;

public class InventoryMapper {
    public static Inventory makeInventoryFromInventoryDTO(InventoryDTO inventoryDTO) {
        return Inventory.builder()
                .date(getLocalDateTimeFromTimeStamp(inventoryDTO.getDate()))
                .amountExcludingGST(new BigDecimal(inventoryDTO.getAmountExcludingGst()))
                .gstRate(new BigDecimal(inventoryDTO.getGstRate()))
                .gstAmount(new BigDecimal(inventoryDTO.getGstAmount()))
                .amountIncludingGST(new BigDecimal(inventoryDTO.getAmountIncludingGst()))
                .link(inventoryDTO.getLink())
                .discountedState(DiscountedState.valueOf(inventoryDTO.getDiscountedState().name().toString()))
                .inventoryState(InventoryState.valueOf(inventoryDTO.getInventoryState().name().toString()))
                .build();
    }

    public static InventoryDTO makeInventoryDTOfromInventory(Inventory inventory) {
        InventoryDTO.Builder inventoryDTOBuilder = InventoryDTO.newBuilder()
                .setId(inventory.getId())
                .setDate(getTimeStampFromLocalDateTime(inventory.getDate()))
                .setAmountExcludingGst(inventory.getAmountExcludingGST().toString())
                .setGstRate(inventory.getGstRate().toString())
                .setGstAmount(inventory.getGstAmount().toString())
                .setAmountIncludingGst(inventory.getAmountIncludingGST().toString())
                .setLink(inventory.getLink())
                .setDiscountedState(com.example.inventoryService.DiscountedState.valueOf(inventory.getDiscountedState().name())) // Assuming DiscountedState enum exists in Java
                .setInventoryState(com.example.inventoryService.InventoryState.valueOf(inventory.getInventoryState().name())); // Assuming InventoryState enum exists in Java

        return inventoryDTOBuilder.build();
    }

}
