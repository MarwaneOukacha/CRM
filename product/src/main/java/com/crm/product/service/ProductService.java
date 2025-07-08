package com.crm.product.service;

import com.crm.product.entities.dto.*;
import com.crm.product.entities.dto.request.ProductRequestDTO;
import com.crm.product.entities.dto.request.ProductUpdateRequestDTO;
import com.crm.product.entities.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponseDTO create(ProductRequestDTO dto);
    ProductResponseDTO getById(String id);
    Page<ProductResponseDTO> search(SearchProductCriteria criteria, Pageable pageable);
    void delete(String id);
    ProductResponseDTO update(String id, ProductUpdateRequestDTO dto);
}
