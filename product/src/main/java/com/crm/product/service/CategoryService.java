package com.crm.product.service;

import com.crm.product.entities.Category;
import com.crm.product.entities.dto.SearchCategoryCriteria;
import com.crm.product.entities.dto.request.CategoryAddRequestDTO;
import com.crm.product.entities.dto.request.CategoryUpdateRequestDTO;
import com.crm.product.entities.dto.response.CategoryResponseDTO;
import com.crm.product.entities.dto.response.CategoryUpdateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryResponseDTO create(CategoryAddRequestDTO dto);

    CategoryUpdateResponseDTO update(String id, CategoryUpdateRequestDTO dto);

    void delete(String id);

    CategoryResponseDTO getById(String id);

    Page<CategoryResponseDTO> search(SearchCategoryCriteria criteria, Pageable pageable);
}
