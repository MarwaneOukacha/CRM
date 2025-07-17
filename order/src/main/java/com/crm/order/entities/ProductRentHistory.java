package com.crm.order.entities;

import com.crm.order.entities.model.AbstractEntity;
import com.crm.order.enums.RentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "product_rent_history")
@Data
public class ProductRentHistory extends AbstractEntity {

    private String productCode;
    private String customerCode;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private LocalDate actualReturnDate;
    private Double penaltyFee;
    private Double damageFee;

    @Enumerated(EnumType.STRING)
    private RentStatus status;

    // Getters, Setters, Constructors
}
