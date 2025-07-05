package com.crm.product.dao.impl;

import com.crm.product.dao.ProductDao;
import com.crm.product.entities.Media;
import com.crm.product.entities.Product;
import com.crm.product.entities.dto.SearchProductCriteria;
import com.crm.product.entities.dto.request.MediaRequestDTO;
import com.crm.product.entities.dto.request.ProductRequestDTO;
import com.crm.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;


@Service
@RequiredArgsConstructor
@ToString
@Slf4j
public class ProductDaoImpl implements ProductDao {

    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        log.info("Saving product: {}", product);
        Product save = productRepository.save(product);
        log.debug("Product saved successfully with ID: {}", product.getId());
        return save;
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
                        cb.like(cb.lower(root.get("description")), keyword),
                        cb.like(cb.lower(root.get("material")), keyword)
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
    public Product updateProduct(String id, ProductRequestDTO updatedDto) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            log.error("Invalid UUID format: {}", id);
            throw new IllegalArgumentException("Invalid product ID format");
        }

        Product existingProduct = productRepository.findById(uuid)
                .orElseThrow(() -> {
                    log.warn("Product not found with ID: {}", id);
                    return new EntityNotFoundException("Product not found with ID: " + id);
                });

        // Update fields only if not null (or not blank for strings)
        if (updatedDto.getName() != null && !updatedDto.getName().isBlank()) {
            log.debug("Updating name: '{}' -> '{}'", existingProduct.getName(), updatedDto.getName());
            existingProduct.setName(updatedDto.getName());
        } else {
            log.debug("Skipping update of name");
        }

        if (updatedDto.getDescription() != null && !updatedDto.getDescription().isBlank()) {
            log.debug("Updating description: '{}' -> '{}'", existingProduct.getDescription(), updatedDto.getDescription());
            existingProduct.setDescription(updatedDto.getDescription());
        } else {
            log.debug("Skipping update of description");
        }

        if (updatedDto.getMaterial() != null && !updatedDto.getMaterial().isBlank()) {
            log.debug("Updating material: '{}' -> '{}'", existingProduct.getMaterial(), updatedDto.getMaterial());
            existingProduct.setMaterial(updatedDto.getMaterial());
        } else {
            log.debug("Skipping update of material");
        }

        if (updatedDto.getPrice() != null) {
            log.debug("Updating price: '{}' -> '{}'", existingProduct.getPrice(), updatedDto.getPrice());
            existingProduct.setPrice(updatedDto.getPrice());
        } else {
            log.debug("Skipping update of price");
        }

        if (updatedDto.getCategory() != null) {
            log.debug("Updating category: '{}' -> '{}'", existingProduct.getCategory(), updatedDto.getCategory());
            existingProduct.setCategory(updatedDto.getCategory());
        } else {
            log.debug("Skipping update of category");
        }

        if (updatedDto.getStatus() != null) {
            log.debug("Updating status: '{}' -> '{}'", existingProduct.getStatus(), updatedDto.getStatus());
            existingProduct.setStatus(updatedDto.getStatus());
        } else {
            log.debug("Skipping update of status");
        }

        if (updatedDto.getStockQuantity() != null) {
            log.debug("Updating stockQuantity: '{}' -> '{}'", existingProduct.getStockQuantity(), updatedDto.getStockQuantity());
            existingProduct.setStockQuantity(updatedDto.getStockQuantity());
        } else {
            log.debug("Skipping update of stockQuantity");
        }

        if (updatedDto.getPartnerId() != null &&
                (existingProduct.getPartnerId() == null ||
                        !existingProduct.getPartnerId().toString().equals(updatedDto.getPartnerId()))) {
            log.warn("Changing partnerId is not allowed: {}", updatedDto.getPartnerId());
            throw new IllegalStateException("Changing partner ID is not allowed");
        } else {
            log.debug("PartnerId remains unchanged: '{}'", existingProduct.getPartnerId());
        }

        // Update characteristics map
        if (updatedDto.getCharacteristics() != null) {
            Map<String, String> existingCharacteristics = existingProduct.getCharacteristics();
            updatedDto.getCharacteristics().forEach((key, value) -> {
                log.debug("Updating characteristic '{}' : '{}'", key, value);
                existingCharacteristics.put(key, value);
            });
        } else {
            log.debug("Skipping update of characteristics");
        }

        // Update media
        if (updatedDto.getMedia() != null) {
            List<Media> updatedMedia = updatedDto.getMedia().stream().map(mediaDto -> {
                Media media = new Media();
                media.setUrl(mediaDto.getUrl());
                media.setType(mediaDto.getType());
                media.setName(mediaDto.getName());
                media.setProduct(existingProduct);
                return media;
            }).collect(Collectors.toList());

            log.debug("Replacing media list with {} items", updatedMedia.size());
            existingProduct.setMedia(updatedMedia);
        } else {
            log.debug("Skipping update of media");
        }

        Product savedProduct = productRepository.save(existingProduct);
        log.info("Product updated successfully: {}", savedProduct);
        return savedProduct;
    }


}


