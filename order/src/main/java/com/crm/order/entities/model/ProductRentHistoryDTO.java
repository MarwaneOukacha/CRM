package com.crm.order.entities.model;

import com.crm.order.enums.RentStatus;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Data
@ToString
public class ProductRentHistoryDTO {
    private UUID id;
    private String productCode;
    private String customerCode;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private LocalDate actualReturnDate;
    private Double penaltyFee;
    private Double damageFee;
    private RentStatus status;
}
