package com.crm.product.entities.dto;

import com.crm.product.enums.Category;
import com.crm.product.enums.ProductStatus;
import lombok.Data;

@Data
public class SearchProductCriteria {
    private String keyword;
    private Category category;
    private ProductStatus status;
    private Double minPrice;
    private Double maxPrice;
}
