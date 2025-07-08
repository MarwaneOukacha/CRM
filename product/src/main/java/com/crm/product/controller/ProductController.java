package com.crm.product.controller;

import com.crm.product.entities.dto.SearchProductCriteria;
import com.crm.product.entities.dto.request.ProductRequestDTO;
import com.crm.product.entities.dto.request.ProductUpdateRequestDTO;
import com.crm.product.entities.dto.response.ProductResponseDTO;
import com.crm.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponseDTO create(@RequestBody ProductRequestDTO productRequestDTO) {
        log.info("Received request to create product: {}", productRequestDTO);
        return productService.create(productRequestDTO);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable String id) {
        log.info("Received request to get product by id: {}", id);
        return productService.getById(id);
    }

    @GetMapping
    public Page<ProductResponseDTO> search(SearchProductCriteria criteria, Pageable pageable) {
        log.info("Received request to search products with criteria: {}", criteria);
        return productService.search(criteria, pageable);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable String id, @RequestBody ProductUpdateRequestDTO dto) {
        log.info("Received request to update product with id {}: {}", id, dto);
        return productService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("Received request to delete product with id: {}", id);
        productService.delete(id);
    }
}
