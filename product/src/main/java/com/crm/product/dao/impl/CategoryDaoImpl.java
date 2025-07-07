package com.crm.product.dao.impl;

import com.crm.product.dao.CategoryDao;
import com.crm.product.entities.Category;
import com.crm.product.entities.dto.SearchCategoryCriteria;
import com.crm.product.entities.dto.request.CategoryUpdateRequestDTO;
import com.crm.product.mapper.CategoryMapper;
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
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    public Page<Category> findAllWithCriteria(SearchCategoryCriteria criteria, Pageable pageable) {
        log.info("Searching categories with criteria: {}", criteria);

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
        log.debug("Found {} categories matching criteria", result.getTotalElements());
        return result;
    }



    @Override
    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category updateCategory(String id, CategoryUpdateRequestDTO updatedDto) {
        Category existing = findById(UUID.fromString(id));
        if(updatedDto.getName()!=null && !updatedDto.getName().isEmpty()){
            existing.setName(updatedDto.getName());
        }
        if(updatedDto.getParent()!=null && !updatedDto.getParent().isEmpty()){
            existing.setParent(updatedDto.getParent());
        }
        return categoryRepository.save(existing);
    }
}
