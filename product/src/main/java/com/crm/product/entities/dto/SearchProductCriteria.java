package com.crm.product.entities.dto;

import com.crm.product.entities.Category;
import com.crm.product.enums.ProductStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SearchProductCriteria {
    private String keyword;
    private Category category;
    private ProductStatus status;
    private String material;
    private Double minPrice;
    private Double maxPrice;
}
