package com.crm.product.service.impl;

import com.crm.product.dao.ProductDao;
import com.crm.product.entities.Product;
import com.crm.product.entities.dto.SearchProductCriteria;
import com.crm.product.entities.dto.request.ProductRequestDTO;
import com.crm.product.entities.dto.response.ProductResponseDTO;
import com.crm.product.entities.dto.response.ProductSearchResponseDTO;
import com.crm.product.mapper.ProductMapper;
import com.crm.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO dto) {
        log.info("Adding product: {}", dto);

        Product product = productMapper.toEntity(dto);

        // Set the back-reference in Media entities before saving
        if (product.getMedia() != null) {
            product.getMedia().forEach(media -> media.setProduct(product));
        }

        Product savedProduct = productDao.save(product);

        log.debug("Product saved with ID: {}", savedProduct.getId());
        return productMapper.toResponseDTO(savedProduct);
    }


    @Override
    public ProductResponseDTO getByID(String id) {
        log.info("Getting product by ID: {}", id);
        Product product = productDao.findById(UUID.fromString(id));
        if (product == null) {
            log.warn("Product not found for ID: {}", id);
            throw new EntityNotFoundException("Product not found");
        }
        return productMapper.toResponseDTO(product);
    }

    @Override
    public Page<ProductSearchResponseDTO> findAllWithCriteria(SearchProductCriteria criteria, Pageable pageable) {
        log.info("Searching products with criteria: {}", criteria);
        return productDao.findAllWithCriteria(criteria, pageable)
                .map(productMapper::toSearchResponseDTO);
    }

    @Override
    public void remove(String id) {
        log.info("Removing product with ID: {}", id);
        productDao.delete(UUID.fromString(id));
        log.debug("Product removed with ID: {}", id);
    }

    @Override
    public Page<ProductSearchResponseDTO> getByPartnerId(String partnerId, Pageable pageable) {
        log.info("Getting products by partner ID: {}", partnerId);
        return productDao.findByPartnerId(UUID.fromString(partnerId), pageable)
                .map(productMapper::toSearchResponseDTO);
    }

    @Override
    public ProductResponseDTO updateProduct(String id, ProductRequestDTO updatedDto) {
        log.info("Updating product with ID: {}", id);

        Product existingProduct = productDao.updateProduct(id, updatedDto);
        log.info("Product updated successfully: {}", existingProduct);


        return productMapper.toResponseDTO(existingProduct);
    }


}

