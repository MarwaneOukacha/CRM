package com.crm.product.service;

import com.crm.product.entities.dto.SearchProductCriteria;
import com.crm.product.entities.dto.request.AddProductRequestDTO;
import com.crm.product.entities.dto.response.ProductResponseDTO;
import com.crm.product.entities.dto.response.ProductSearchResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    void addProduct(AddProductRequestDTO product);

    ProductResponseDTO getByID(String id);

    Page<ProductSearchResponseDTO> findAllWithCriteria(SearchProductCriteria criteria, Pageable pageable);

    void remove(String id);
    ProductResponseDTO updateProduct(String id, AddProductRequestDTO updatedDto);

    Page<ProductSearchResponseDTO> getByPartnerId(String partnerId, Pageable pageable);
}
