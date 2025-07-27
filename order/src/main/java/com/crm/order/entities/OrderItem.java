package com.crm.order.entities;

import com.crm.order.entities.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private String productId;
    private String productCode;
    private Double quantity;
    private Double pricePerUnit;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;

}
