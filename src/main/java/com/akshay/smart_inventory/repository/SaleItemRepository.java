package com.akshay.smart_inventory.repository;

import com.akshay.smart_inventory.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
}