package com.crm.order.entities.model;

import com.crm.order.enums.Status;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderStatusHistoryDTO {
    private UUID id;
    private Status status;
}