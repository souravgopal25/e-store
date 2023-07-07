package com.example.inventoryservice.entity;

import com.example.inventoryservice.enums.DiscountedState;
import com.example.inventoryservice.enums.InventoryState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@Entity(name = "inventory")
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Invoice Date
    private LocalDateTime date;
    private BigDecimal amountExcludingGST;
    private BigDecimal gstRate;
    private BigDecimal gstAmount;
    private BigDecimal amountIncludingGST;
    private String link;
    private DiscountedState discountedState;
    private InventoryState inventoryState;
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
