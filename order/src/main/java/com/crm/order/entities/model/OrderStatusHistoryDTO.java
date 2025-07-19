package com.crm.order.entities.model;

import com.crm.order.enums.OrderStatus;
import com.crm.order.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderStatusHistoryDTO {
    private UUID id;
    private OrderStatus status;
    private LocalDateTime created;
}