package com.akshay.smart_inventory.service.alert;

import com.akshay.smart_inventory.model.*;
import com.akshay.smart_inventory.repository.AlertRepository;
import com.akshay.smart_inventory.repository.BatchRepository;
import com.akshay.smart_inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService implements IAlertService {

    private final AlertRepository alertRepository;
    private final ProductRepository productRepository;
    private final BatchRepository batchRepository;

    @Override
    public void scanAndGenerateAlerts() {
        generateLowStockAlerts();
        generateHighRiskAlerts();
        generateExpiringSoonAlerts();
    }

    private void generateLowStockAlerts() {

        productRepository.findAll().forEach(product -> {

            Integer totalStock =
                    batchRepository.getTotalValidStockByProductId(product.getId());

            if (totalStock < product.getReorderLevel()) {

                String message = "Low stock for product: " + product.getName();

                if (!alertRepository.existsByTypeAndMessageAndResolvedFalse(
                        AlertType.LOW_STOCK,
                        message)) {

                    Alert alert = new Alert();
                    alert.setType(AlertType.LOW_STOCK);
                    alert.setMessage(message);
                    alert.setSeverity(SeverityLevel.HIGH);
                    alert.setCreatedAt(LocalDateTime.now());

                    alertRepository.save(alert);
                }
            }
        });
    }

    private void generateHighRiskAlerts() {

        batchRepository.findAll().forEach(batch -> {

            long daysLeft =
                    ChronoUnit.DAYS.between(LocalDate.now(), batch.getExpiryDate());

            if (batch.getQuantity() > 0 && daysLeft <= 7 && daysLeft >= 0) {

                String message = "High risk batch: " + batch.getBatchNumber();

                if (!alertRepository.existsByTypeAndMessageAndResolvedFalse(
                        AlertType.HIGH_RISK,
                        message)) {

                    Alert alert = new Alert();
                    alert.setType(AlertType.HIGH_RISK);
                    alert.setMessage(message);
                    alert.setSeverity(SeverityLevel.MEDIUM);
                    alert.setCreatedAt(LocalDateTime.now());

                    alertRepository.save(alert);
                }
            }
        });
    }

    private void generateExpiringSoonAlerts() {

        batchRepository.findAll().forEach(batch -> {

            long daysLeft =
                    ChronoUnit.DAYS.between(LocalDate.now(), batch.getExpiryDate());

            if (batch.getQuantity() > 0 && daysLeft <= 15 && daysLeft >= 0) {

                String message = "Batch expiring soon: " + batch.getBatchNumber();

                if (!alertRepository.existsByTypeAndMessageAndResolvedFalse(
                        AlertType.EXPIRING_SOON,
                        message)) {

                    Alert alert = new Alert();
                    alert.setType(AlertType.EXPIRING_SOON);
                    alert.setMessage(message);
                    alert.setSeverity(SeverityLevel.LOW);
                    alert.setCreatedAt(LocalDateTime.now());

                    alertRepository.save(alert);
                }
            }
        });
    }

    @Override
    public List<Alert> getActiveAlerts() {
        return alertRepository.findByResolvedFalse();
    }

    @Override
    public void resolveAlert(Long alertId) {

        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setResolved(true);
        alertRepository.save(alert);
    }
}