package com.crm.product.entities.dto;

import com.crm.product.entities.dto.response.MediaResponseDTO;
import com.crm.product.enums.Category;
import com.crm.product.enums.ProductStatus;
import lombok.Data;

import java.util.List;

@Data
public class SearchProductCriteria {
    private String keyword;
    private Category category;
    private ProductStatus status;
    private String material;
    private Double minPrice;
    private Double maxPrice;
}
