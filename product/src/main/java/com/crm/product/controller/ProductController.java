package com.crm.product.controller;

import com.crm.product.entities.dto.SearchProductCriteria;
import com.crm.product.entities.dto.request.AddProductRequestDTO;
import com.crm.product.entities.dto.response.ProductResponseDTO;
import com.crm.product.entities.dto.response.ProductSearchResponseDTO;
import com.crm.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public void addProduct(@RequestBody AddProductRequestDTO request) {
        log.info("Received request to add product: {}", request);
        productService.addProduct(request);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable String id) {
        log.info("Fetching product with ID: {}", id);
        return productService.getByID(id);
    }

    @GetMapping
    public Page<ProductSearchResponseDTO> searchProducts(
            SearchProductCriteria criteria,
            Pageable pageable
    ) {
        log.info("Searching products with criteria: {}", criteria);
        return productService.findAllWithCriteria(criteria, pageable);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        log.info("Deleting product with ID: {}", id);
        productService.remove(id);
    }

    @GetMapping("/partner/{partnerId}")
    public Page<ProductSearchResponseDTO> getByPartner(
            @PathVariable String partnerId,
            Pageable pageable
    ) {
        log.info("Fetching products for partner ID: {}", partnerId);
        return productService.getByPartnerId(partnerId, pageable);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(
            @PathVariable String id,
            @RequestBody AddProductRequestDTO request
    ) {
        log.info("Updating product with ID: {} and payload: {}", id, request);
        return productService.updateProduct(id, request);
    }
}
