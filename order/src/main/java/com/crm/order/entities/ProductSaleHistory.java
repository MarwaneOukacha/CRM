package com.crm.order.entities;

import com.crm.order.entities.model.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "product_sale_history")
@Data
public class ProductSaleHistory extends AbstractEntity {


    private String productCode;
    private String customerCode;
    private LocalDate saleDate;
    private Double price;
}
