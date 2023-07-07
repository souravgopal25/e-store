package com.example.inventoryservice.service;

import com.example.inventoryservice.entity.Deal;
import com.example.inventoryservice.exception.DealNotFoundException;
import com.example.inventoryservice.repository.DealRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class DealService {
    private final DealRepository dealRepository;

    public DealService(DealRepository dealRepository) {
        this.dealRepository = dealRepository;
    }


    public Deal createDeal(Deal deal) {
        return dealRepository.save(deal);
    }


    public Deal updateDeal(Long dealId, Deal updatedDeal) {
        return dealRepository.findById(dealId)
                .map(deal -> {
                    deal.setName(updatedDeal.getName());
                    deal.setInventoryId(updatedDeal.getInventoryId());
                    deal.setStartRaisingDate(updatedDeal.getStartRaisingDate());
                    deal.setCloseRaisingDate(updatedDeal.getCloseRaisingDate());
                    return dealRepository.save(deal);
                })
                .orElseThrow(() -> new DealNotFoundException(dealId));
    }


    public Optional<Deal> getDealById(Long id) {
        return dealRepository.findById(id);
    }


    public List<Deal> getAllDeals() {
        return dealRepository.findAll();
    }


    public void deleteDeal(Long dealId) {
        dealRepository.findById(dealId)
                .orElseThrow(() -> new DealNotFoundException(dealId));
        dealRepository.deleteById(dealId);
    }
}
