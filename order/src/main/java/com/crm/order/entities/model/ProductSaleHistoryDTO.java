package com.crm.order.entities.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Data
@ToString
public class ProductSaleHistoryDTO {
    private UUID id;
    private String productCode;
    private String customerCode;
    private LocalDate saleDate;
    private Double price;
}