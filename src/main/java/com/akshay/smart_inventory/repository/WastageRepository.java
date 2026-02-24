package com.akshay.smart_inventory.repository;

import com.akshay.smart_inventory.model.Wastage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface WastageRepository extends JpaRepository<Wastage, Long> {
    @Query("SELECT COALESCE(SUM(w.totalLossAmount), 0) FROM Wastage w")
    BigDecimal getTotalLossAmount();

    @Query("SELECT COALESCE(SUM(w.quantity), 0) FROM Wastage w")
    Integer getTotalWastedQuantity();
}