package com.crm.order.entities.dto.response;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Data
@ToString
public class OrderItemResponseDTO {
    private UUID id;
    private String productId;
    private String productCode;
    private Double quantity;
    private Double pricePerUnit;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
}
