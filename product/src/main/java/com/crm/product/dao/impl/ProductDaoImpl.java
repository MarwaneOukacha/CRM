package com.crm.product.dao.impl;

import com.crm.product.dao.ProductDao;
import com.crm.product.entities.Product;
import com.crm.product.entities.dto.SearchProductCriteria;
import com.crm.product.entities.dto.request.AddProductRequestDTO;
import com.crm.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;



import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@ToString
@Slf4j
public class ProductDaoImpl implements ProductDao {

    private final ProductRepository productRepository;

    @Override
    public void save(Product product) {
        log.info("Saving product: {}", product);
        productRepository.save(product);
        log.debug("Product saved successfully with ID: {}", product.getId());
    }

    @Override
    public Product findById(UUID id) {
        log.info("Finding product by ID: {}", id);
        return productRepository.findById(id)
                .map(product -> {
                    log.debug("Product found: {}", product);
                    return product;
                })
                .orElseGet(() -> {
                    log.warn("No product found with ID: {}", id);
                    return null;
                });
    }

    @Override
    public Page<Product> findAllWithCriteria(SearchProductCriteria criteria, Pageable pageable) {
        log.info("Searching products with criteria: {}", criteria);

        Specification<Product> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
                String keyword = "%" + criteria.getKeyword().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), keyword),
                        cb.like(cb.lower(root.get("description")), keyword)
                ));
            }

            if (criteria.getCategory() != null) {
                predicates.add(cb.equal(root.get("category"), criteria.getCategory()));
            }

            if (criteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }

            if (criteria.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice()));
            }

            if (criteria.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Product> result = productRepository.findAll(specification, pageable);
        log.debug("Found {} products matching criteria", result.getTotalElements());
        return result;
    }

    @Override
    public void delete(UUID id) {
        log.info("Deleting product by ID: {}", id);
        productRepository.deleteById(id);
        log.debug("Product with ID {} deleted", id);
    }

    @Override
    public Page<Product> findByPartnerId(UUID partnerId, Pageable pageable) {
        log.info("Finding products by partner ID: {}", partnerId);
        Page<Product> result = productRepository.findByPartnerId(partnerId, pageable);
        log.debug("Found {} products for partner ID {}", result.getTotalElements(), partnerId);
        return result;
    }

    @Override
    public Product updateProduct(String id, AddProductRequestDTO updatedDto) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID format: {}", id);
            throw new IllegalArgumentException("Invalid product ID format");
        }

        Product existingProduct = productRepository.findById(UUID.fromString(id)).get();
        if (existingProduct == null) {
            log.warn("Product not found with ID: {}", id);
            throw new EntityNotFoundException("Product not found with ID: " + id);
        }

        // Update fields only if not null (or not blank for strings)
        if (updatedDto.getName() != null && !updatedDto.getName().isBlank()) {
            existingProduct.setName(updatedDto.getName());
        }

        if (updatedDto.getDescription() != null && !updatedDto.getDescription().isBlank()) {
            existingProduct.setDescription(updatedDto.getDescription());
        }

        if (updatedDto.getPrice() != null) {
            existingProduct.setPrice(updatedDto.getPrice());
        }

        if (updatedDto.getCategory() != null ) {
            existingProduct.setCategory(updatedDto.getCategory());
        }

        if (updatedDto.getStatus() != null ) {
            existingProduct.setStatus(updatedDto.getStatus());
        }

        // Optional: block changing partnerId
        if (updatedDto.getPartnerId() != null &&
                !existingProduct.getPartnerId().toString().equals(updatedDto.getPartnerId())) {
            log.warn("Changing partnerId is not allowed: {}", updatedDto.getPartnerId());
            throw new IllegalStateException("Changing partner ID is not allowed");
        }

        return productRepository.save(existingProduct);
    }
}


