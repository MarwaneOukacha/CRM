package com.crm.product.entities.dto.response;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private String id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String status;
}
