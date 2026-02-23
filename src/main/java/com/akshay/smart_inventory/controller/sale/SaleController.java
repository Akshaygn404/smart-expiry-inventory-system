package com.akshay.smart_inventory.controller.sale;

import com.akshay.smart_inventory.dto.request.SaleRequest;
import com.akshay.smart_inventory.dto.response.SaleResponse;
import com.akshay.smart_inventory.service.sale.ISaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/sales")
@RequiredArgsConstructor
public class SaleController {

    private final ISaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(
            @Valid @RequestBody SaleRequest request) {

        return ResponseEntity.ok(saleService.createSale(request));
    }
}