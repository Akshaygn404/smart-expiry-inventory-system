package com.akshay.smart_inventory.controller.alert;

import com.akshay.smart_inventory.model.Alert;
import com.akshay.smart_inventory.service.alert.IAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final IAlertService alertService;

    @PostMapping("/scan")
    public ResponseEntity<String> scanAlerts() {
        alertService.scanAndGenerateAlerts();
        return ResponseEntity.ok("Alert scan completed");
    }

    @GetMapping
    public ResponseEntity<List<Alert>> getActiveAlerts() {
        return ResponseEntity.ok(alertService.getActiveAlerts());
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<String> resolveAlert(@PathVariable Long id) {
        alertService.resolveAlert(id);
        return ResponseEntity.ok("Alert resolved");
    }
}