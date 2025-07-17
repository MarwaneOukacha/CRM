package com.crm.order.entities;

import com.crm.order.entities.model.AbstractEntity;
import com.crm.order.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "order_status_history")
@Data
public class OrderStatusHistory extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Status status;

}
