package com.crm.product.entities.dto.response;

import lombok.Data;

@Data
public class ProductSearchResponseDTO {
    private String id;
    private String name;
    private Double price;
    private String status;
}
