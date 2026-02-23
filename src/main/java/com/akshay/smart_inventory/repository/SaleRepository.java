package com.akshay.smart_inventory.repository;

import com.akshay.smart_inventory.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}