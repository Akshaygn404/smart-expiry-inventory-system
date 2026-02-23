package com.akshay.smart_inventory.service.sale;

import com.akshay.smart_inventory.dto.request.SaleRequest;
import com.akshay.smart_inventory.dto.response.SaleResponse;

public interface ISaleService {

    SaleResponse createSale(SaleRequest request);

}