package com.crm.order.entities;


import com.crm.order.entities.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
public class Message extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String sender; // "admin" or "client"

    @Column(columnDefinition = "TEXT")
    private String text;

    private LocalDateTime timestamp;
}
