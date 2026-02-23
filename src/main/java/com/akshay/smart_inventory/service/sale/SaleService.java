package com.akshay.smart_inventory.service.sale;

import com.akshay.smart_inventory.dto.request.SaleRequest;
import com.akshay.smart_inventory.dto.response.SaleResponse;
import com.akshay.smart_inventory.exception.ResourceNotFoundException;
import com.akshay.smart_inventory.model.Product;
import com.akshay.smart_inventory.model.Sale;
import com.akshay.smart_inventory.repository.ProductRepository;
import com.akshay.smart_inventory.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SaleService implements ISaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    @Override
    public SaleResponse createSale(SaleRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setTotalQuantity(request.getQuantity());
        sale.setSaleDate(LocalDateTime.now());

        Sale savedSale = saleRepository.save(sale);

        return mapToResponse(savedSale);
    }

    private SaleResponse mapToResponse(Sale sale) {

        SaleResponse response = new SaleResponse();
        response.setSaleId(sale.getId());
        response.setProductId(sale.getProduct().getId());
        response.setProductName(sale.getProduct().getName());
        response.setTotalQuantity(sale.getTotalQuantity());
        response.setSaleDate(sale.getSaleDate());

        return response;
    }
}