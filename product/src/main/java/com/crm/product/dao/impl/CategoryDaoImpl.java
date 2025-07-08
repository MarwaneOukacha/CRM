package com.crm.product.dao.impl;

import com.crm.product.dao.CategoryDao;
import com.crm.product.entities.Category;
import com.crm.product.entities.dto.SearchCategoryCriteria;
import com.crm.product.entities.dto.request.CategoryUpdateRequestDTO;
import com.crm.product.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryDaoImpl implements CategoryDao {

    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        log.info("CategoryDaoImpl::save - Saving category: {}", category);
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(UUID id) {
        log.info("CategoryDaoImpl::findById - Finding category by id: {}", id);
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    public Page<Category> findAllWithCriteria(SearchCategoryCriteria criteria, Pageable pageable) {
        log.info("CategoryDaoImpl::findAllWithCriteria - Searching categories with criteria: {}", criteria);

        Specification<Category> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getName() != null && !criteria.getName().isEmpty()) {
                String keyword = "%" + criteria.getName().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("name")), keyword));
            }

            if (criteria.getStatus() != null && !criteria.getStatus().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("status")), criteria.getStatus().toLowerCase()));
            }
            if (criteria.getParent() != null && !criteria.getParent().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("parent")), criteria.getParent().toLowerCase()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Category> result = categoryRepository.findAll(specification, pageable);
        log.info("CategoryDaoImpl::findAllWithCriteria - Found {} categories", result.getTotalElements());
        return result;
    }

    @Override
    public void delete(UUID id) {
        log.info("CategoryDaoImpl::delete - Deleting category with id: {}", id);
        categoryRepository.deleteById(id);
        log.info("CategoryDaoImpl::delete - Successfully deleted category with id: {}", id);
    }

    @Override
    public Category updateCategory(String id, CategoryUpdateRequestDTO updatedDto) {
        log.info("CategoryDaoImpl::updateCategory - Updating category with id: {}, data: {}", id, updatedDto);
        Category existing = findById(UUID.fromString(id));
        if(updatedDto.getName()!=null && !updatedDto.getName().isEmpty()){
            existing.setName(updatedDto.getName());
        }
        if(updatedDto.getParent()!=null && !updatedDto.getParent().isEmpty()){
            existing.setParent(updatedDto.getParent());
        }
        Category updated = categoryRepository.save(existing);
        log.info("CategoryDaoImpl::updateCategory - Updated category with id: {}", updated.getId());
        return updated;
    }
}
