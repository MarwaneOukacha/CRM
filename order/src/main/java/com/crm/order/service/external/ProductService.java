package com.crm.order.service.external;

import com.crm.order.entities.dto.request.ProductUpdateRequestDTO;
import com.crm.order.entities.dto.response.ProductResponseDTO;

public interface ProductService {
    ProductResponseDTO update(String id, ProductUpdateRequestDTO dto);
}
