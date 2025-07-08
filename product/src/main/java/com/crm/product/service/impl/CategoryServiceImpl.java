package com.crm.product.service.impl;

import com.crm.product.dao.CategoryDao;
import com.crm.product.entities.Category;
import com.crm.product.entities.dto.SearchCategoryCriteria;
import com.crm.product.entities.dto.request.CategoryAddRequestDTO;
import com.crm.product.entities.dto.request.CategoryUpdateRequestDTO;
import com.crm.product.entities.dto.response.CategoryResponseDTO;
import com.crm.product.entities.dto.response.CategoryUpdateResponseDTO;
import com.crm.product.mapper.CategoryMapper;
import com.crm.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDTO create(CategoryAddRequestDTO dto) {
        log.info("CategoryServiceImpl::create - Creating new category: {}", dto);
        Category category = categoryMapper.fromAddDto(dto);
        Category save = categoryDao.save(category);
        return categoryMapper.toResponseDto(category);
    }

    @Override
    public CategoryUpdateResponseDTO update(String id, CategoryUpdateRequestDTO dto) {
        log.info("CategoryServiceImpl::update - Updating category with id: {}", id);
        Category category = categoryDao.findById(UUID.fromString(id));

        if (dto.getName() != null && !dto.getName().isEmpty()) {
            category.setName(dto.getName());
        }

        if (dto.getParent() != null && !dto.getParent().isEmpty()) {
            category.setParent(dto.getParent());
        }

        Category updated = categoryDao.save(category);
        log.info("CategoryServiceImpl::update - Updated category with id: {}", updated.getId());

        return categoryMapper.toCategoryUpdateResponseDTO(updated);
    }

    @Override
    public void delete(String id) {
        log.info("CategoryServiceImpl::delete - Deleting category with id: {}", id);
        categoryDao.delete(UUID.fromString(id));
        log.info("CategoryServiceImpl::delete - Deleted category with id: {}", id);
    }

    @Override
    public CategoryResponseDTO getById(String id) {
        log.info("CategoryServiceImpl::getById - Fetching category with id: {}", id);
        Category category = categoryDao.findById(UUID.fromString(id));
        return categoryMapper.toResponseDto(category);
    }

    @Override
    public Page<CategoryResponseDTO> search(SearchCategoryCriteria criteria, Pageable pageable) {
        log.info("CategoryServiceImpl::search - Searching categories with criteria: {}", criteria);
        return categoryDao.findAllWithCriteria(criteria, pageable)
                .map(categoryMapper::toResponseDto);
    }
}
