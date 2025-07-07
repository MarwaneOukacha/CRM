package com.crm.product.dao;

import com.crm.product.entities.Category;
import com.crm.product.entities.dto.SearchCategoryCriteria;
import com.crm.product.entities.dto.request.CategoryUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CategoryDao {
    Category save(Category category);
    Category findById(UUID id);
    Page<Category> findAllWithCriteria(SearchCategoryCriteria criteria, Pageable pageable);
    void delete(UUID id);
    Category updateCategory(String id, CategoryUpdateRequestDTO updatedDto);
}
