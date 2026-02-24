package com.akshay.smart_inventory.repository;

import com.akshay.smart_inventory.model.Alert;
import com.akshay.smart_inventory.model.AlertType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByResolvedFalse();

    boolean existsByTypeAndMessageAndResolvedFalse(
            AlertType type,
            String message
    );

}