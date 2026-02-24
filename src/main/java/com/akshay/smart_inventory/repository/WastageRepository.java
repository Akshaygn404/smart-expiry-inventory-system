package com.akshay.smart_inventory.repository;

import com.akshay.smart_inventory.model.Wastage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface WastageRepository extends JpaRepository<Wastage, Long> {
    @Query("SELECT COALESCE(SUM(w.totalLossAmount), 0) FROM Wastage w")
    BigDecimal getTotalLossAmount();

    @Query("SELECT COALESCE(SUM(w.quantity), 0) FROM Wastage w")
    Integer getTotalWastedQuantity();

    @Query("""
       SELECT YEAR(w.recordedAt),
              MONTH(w.recordedAt),
              SUM(w.quantity),
              SUM(w.totalLossAmount)
       FROM Wastage w
       GROUP BY YEAR(w.recordedAt), MONTH(w.recordedAt)
       ORDER BY YEAR(w.recordedAt), MONTH(w.recordedAt)
       """)
    List<Object[]> getMonthlyWastageStats();

    @Query("""
       SELECT w.batch.product.name,
              SUM(w.quantity),
              SUM(w.totalLossAmount)
       FROM Wastage w
       GROUP BY w.batch.product.name
       ORDER BY SUM(w.totalLossAmount) DESC
       """)
    List<Object[]> getTopWastedProducts();
}