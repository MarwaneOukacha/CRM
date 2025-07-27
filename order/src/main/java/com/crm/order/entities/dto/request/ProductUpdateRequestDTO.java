package com.crm.order.entities.dto.request;

import lombok.Data;

@Data
public class ProductUpdateRequestDTO {
    private double quantity;
    private double quantityRented;
    private double quantitySaled;
}