package com.crm.product.dao;

import com.crm.product.entities.*;
import com.crm.product.entities.dto.SearchProductCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductDao {
    Product save(Product product);
    Product findById(UUID id);
    Page<Product> findAllWithCriteria(SearchProductCriteria criteria, Pageable pageable);
    void delete(UUID id);
    Product updateProduct(UUID id, Product updatedProduct);

    List<ProductDesigner> findDesignersByIds(List<String> designerIds);

    List<ProductColor> findColorsByIds(List<String> colorIds);

    List<ProductMaterial> findMaterialsByIds(List<String> materialIds);

    List<ProductOccasion> findOccasionsByIds(List<String> occasionIds);


}
