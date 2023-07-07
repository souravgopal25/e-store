package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.Inventory;
import com.example.inventoryservice.exception.InventoryNotFoundException;
import com.example.inventoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;


    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }


    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }


    public Inventory updateInventory(Long id, Inventory updateInventory) {
        return inventoryRepository.findById(id).map(
                inventory -> {
                    inventory.setId(id);
                    inventory.setDate(updateInventory.getDate());
                    inventory.setAmountExcludingGST(updateInventory.getAmountExcludingGST());
                    inventory.setAmountIncludingGST(updateInventory.getAmountIncludingGST());
                    inventory.setGstRate(updateInventory.getGstRate());
                    inventory.setGstAmount(updateInventory.getGstAmount());
                    inventory.setLink(updateInventory.getLink());
                    inventory.setDiscountedState(updateInventory.getDiscountedState());
                    inventory.setInventoryState(updateInventory.getInventoryState());
                    return inventoryRepository.save(inventory);
                }).orElseThrow(()->new InventoryNotFoundException(id));

    }


    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }


    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }


    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }
}
