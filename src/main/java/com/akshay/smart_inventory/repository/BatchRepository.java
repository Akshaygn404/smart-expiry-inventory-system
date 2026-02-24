package com.akshay.smart_inventory.repository;

import com.akshay.smart_inventory.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Long> {

    List<Batch> findByProductIdOrderByExpiryDateAsc(Long productId);

    boolean existsByBatchNumberAndProductId(String batchNumber, Long productId);

    List<Batch> findByExpiryDateBefore(LocalDate date);

    List<Batch> findByExpiryDateBetween(LocalDate start, LocalDate end);

    @Query("""
       SELECT COALESCE(SUM(b.quantity), 0)
       FROM Batch b
       WHERE b.product.id = :productId
       AND b.expiryDate >= CURRENT_DATE
       """)
    Integer getTotalValidStockByProductId(Long productId);

}