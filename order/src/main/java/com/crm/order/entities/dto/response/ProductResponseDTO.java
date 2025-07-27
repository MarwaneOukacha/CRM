package com.crm.order.entities.dto.response;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private String productId;
    private double quantity;
    private double quantityRented;
    private double quantitySaled;
}
