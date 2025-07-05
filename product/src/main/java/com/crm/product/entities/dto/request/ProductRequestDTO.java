package com.crm.product.entities.dto.request;

import com.crm.product.enums.Category;
import com.crm.product.enums.ProductStatus;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private String material;
    private Double price;
    private Category category;
    private ProductStatus status;
    private String partnerId;
    private Double stockQuantity;
    private Map<String, String> characteristics;
    private List<MediaRequestDTO> media;
}
