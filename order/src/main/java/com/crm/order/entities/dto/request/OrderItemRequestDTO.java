package com.crm.order.entities.dto.request;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class OrderItemRequestDTO {
    private String productCode;
    private Double pricePerUnit;
    private Double quantity;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
}