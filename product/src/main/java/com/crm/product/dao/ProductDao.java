package com.crm.product.dao;

import com.crm.product.entities.Product;
import com.crm.product.entities.dto.SearchProductCriteria;
import com.crm.product.entities.dto.request.AddProductRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductDao {
    void save(Product product);
    Product findById(UUID id);
    Page<Product> findAllWithCriteria(SearchProductCriteria criteria, Pageable pageable);
    void delete(UUID id);
    Page<Product> findByPartnerId(UUID partnerId,Pageable pageable);

    Product updateProduct(String id, AddProductRequestDTO updatedDto);

}
