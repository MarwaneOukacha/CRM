package com.crm.product.entities.dto.request;

import com.crm.product.enums.Category;
import com.crm.product.enums.ProductStatus;
import lombok.Data;

@Data
public class AddProductRequestDTO {
    private String name;
    private String description;
    private Double price;
    private Category category;
    private ProductStatus status;
    private String partnerId;
}
