package com.crm.product.service.impl;

import com.crm.product.dao.CaratDao;
import com.crm.product.dao.CategoryDao;
import com.crm.product.dao.ProductDao;
import com.crm.product.entities.*;
import com.crm.product.entities.dto.*;
import com.crm.product.entities.dto.request.ProductRequestDTO;
import com.crm.product.entities.dto.request.ProductUpdateRequestDTO;
import com.crm.product.entities.dto.response.ProductResponseDTO;
import com.crm.product.mapper.ProductMapper;
import com.crm.product.service.ProductService;
import com.crm.product.utils.ProductCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final ProductMapper productMapper;
    private final CategoryDao categoryDao;
    private final CaratDao caratDao;
    @Value("${app.file-url}")
    private String uploadDir;


    @Override
    public ProductResponseDTO create(ProductRequestDTO dto) {
        log.info("ProductServiceImpl::create - Creating product with data: {}", dto);

        Product product = productMapper.fromRequestDto(dto);

        // === Fetch and map relations safely ===
        List<ProductOccasion> occasions = productDao.findOccasionsByIds(
                Optional.ofNullable(dto.getOccasionIds())
                        .orElse(List.of())
        );

        List<ProductMaterial> materials = productDao.findMaterialsByIds(
                Optional.ofNullable(dto.getMaterialIds()).orElse(List.of())
        );

        List<ProductColor> colors = productDao.findColorsByIds(
                Optional.ofNullable(dto.getColorIds()).orElse(List.of())
        );

        List<ProductDesigner> designers = productDao.findDesignersByIds(
                Optional.ofNullable(dto.getDesignerIds()).orElse(List.of())
        );

        // Set product to each child entity
        occasions.forEach(o -> o.setProduct(product));
        materials.forEach(m -> m.setProduct(product));
        colors.forEach(c -> c.setProduct(product));
        designers.forEach(d -> d.setProduct(product));

        product.setProductOccasion(occasions);
        product.setProductMaterials(materials);
        product.setProductColors(colors);
        product.setProductDesigners(designers);

        // === Handle media ===
        if (dto.getMedia() != null && !dto.getMedia().isEmpty()) {
            List<Media> media = productMapper.fromMediaRequestDTOList(dto.getMedia(), product);
            media.forEach(m -> {
                m.setProduct(product);
                m.setUrl(uploadDir+m.getName());
            });
            product.setMedia(media);
        }
        String barcodeBase64;
        try {
            ProductCodeAndBarcode productCodeBarCode = ProductCodeGenerator.generateProductCodeAndBarcode();
            product.setCode(productCodeBarCode.getProductCode());
            product.setBarcodeImage(productCodeBarCode.getBarcodeBase64());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate barcode", e);
        }
        Product savedProduct = productDao.save(product);


        ProductResponseDTO responseDTO = productMapper.toResponseDto(savedProduct);

        log.info("ProductServiceImpl::create - Created product with ID: {}", responseDTO.getId());
        return responseDTO;
    }



    @Override
    public ProductResponseDTO getById(String id) {
        log.info("ProductServiceImpl::getById - Fetching product with ID: {}", id);

        Product product = productDao.findById(UUID.fromString(id));
        ProductResponseDTO responseDTO = productMapper.toResponseDto(product);

        log.info("ProductServiceImpl::getById - Found product: {}", responseDTO);
        return responseDTO;
    }

    @Override
    public Page<ProductResponseDTO> search(SearchProductCriteria criteria, Pageable pageable) {
        log.info("ProductServiceImpl::search - Searching products with criteria: {}", criteria);

        Page<Product> page = productDao.findAllWithCriteria(criteria, pageable);
        Page<ProductResponseDTO> dtoPage = page.map(productMapper::toResponseDto);

        log.info("ProductServiceImpl::search - Found {} products", dtoPage.getTotalElements());
        return dtoPage;
    }

    @Override
    public void delete(String id) {
        log.info("ProductServiceImpl::delete - Deleting product with ID: {}", id);

        productDao.delete(UUID.fromString(id));

        log.info("ProductServiceImpl::delete - Deleted product with ID: {}", id);
    }

    @Override
    public ProductResponseDTO update(String id, ProductUpdateRequestDTO dto) {
        log.info("ProductServiceImpl::update - Updating product with ID: {} and data: {}", id, dto);

        Product existingProduct = productDao.findById(UUID.fromString(id));

        // === Basic fields ===
        if (dto.getName() != null) existingProduct.setName(dto.getName());
        if (dto.getDescription() != null) existingProduct.setDescription(dto.getDescription());
        if (dto.getPrice() != null) existingProduct.setPrice(dto.getPrice());
        if (dto.getCode() != null) existingProduct.setCode(dto.getCode());
        if (dto.getClicks() != null) existingProduct.setClicks(dto.getClicks());
        if (dto.getFavorite() != null) existingProduct.setFavorite(dto.getFavorite());
        if (dto.getCart() != null) existingProduct.setCart(dto.getCart());
        if (dto.getSize() != null) existingProduct.setSize(dto.getSize());

        if (dto.getCaratId() != null) {
            Carat carat = caratDao.findById(dto.getCaratId());
            existingProduct.setCarat(carat);
        }

        if (dto.getStatus() != null) existingProduct.setStatus(dto.getStatus());

        if (dto.getCategoryId() != null) {
            Category category = categoryDao.findById(dto.getCategoryId());
            existingProduct.setCategory(category);
        }

        // === Occasions ===
        if (dto.getOccasionIds() != null) {
            List<ProductOccasion> occasions = productDao.findOccasionsByIds(dto.getOccasionIds());
            occasions.forEach(o -> o.setProduct(existingProduct));

            existingProduct.getProductOccasion().clear();
            existingProduct.getProductOccasion().addAll(occasions);
        }
        // === Materials ===
        if (dto.getMaterialIds() != null) {
            List<ProductMaterial> materials = productDao.findMaterialsByIds(dto.getMaterialIds());
            materials.forEach(m -> m.setProduct(existingProduct));
            existingProduct.getProductMaterials().clear();
            existingProduct.getProductMaterials().addAll(materials);
        }

        // === Colors ===
        if (dto.getColorIds() != null) {
            List<ProductColor> colors = productDao.findColorsByIds(dto.getColorIds());
            colors.forEach(c -> c.setProduct(existingProduct));
            existingProduct.getProductColors().clear();
            existingProduct.getProductColors().addAll(colors);
        }

        // === Designers ===
        if (dto.getDesignerIds() != null) {
            List<ProductDesigner> designers = productDao.findDesignersByIds(dto.getDesignerIds());
            designers.forEach(d -> d.setProduct(existingProduct));
            existingProduct.getProductDesigners().clear();
            existingProduct.getProductDesigners().addAll(designers);
        }

        // === Media ===
        if (dto.getMedia() != null) {
            List<Media> mediaList = productMapper.fromMediaRequestDTOList(dto.getMedia(), existingProduct);
            mediaList.forEach(m -> m.setProduct(existingProduct));
            existingProduct.getMedia().clear();
            existingProduct.getMedia().addAll(mediaList);
        }

        if (dto.getAgencyId() != null) existingProduct.setAgencyId(dto.getAgencyId());
        if (dto.getType() != null) existingProduct.setType(dto.getType());
        if (dto.getWeight() != null) existingProduct.setWeight(dto.getWeight());
        if (dto.getPartnerId() != null) existingProduct.setPartnerId(UUID.fromString(dto.getPartnerId()));

        // === Save ===
        Product updatedProduct = productDao.updateProduct(existingProduct.getId(), existingProduct);
        ProductResponseDTO responseDTO = productMapper.toResponseDto(updatedProduct);

        log.info("ProductServiceImpl::update - Updated product: {}", responseDTO);
        return responseDTO;
    }

}
