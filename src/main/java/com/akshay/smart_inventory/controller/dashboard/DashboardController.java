package com.akshay.smart_inventory.controller.dashboard;

import com.akshay.smart_inventory.dto.response.DashboardResponse;
import com.akshay.smart_inventory.service.dashboard.IDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final IDashboardService dashboardService;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(
                dashboardService.getDashboard());
    }
}