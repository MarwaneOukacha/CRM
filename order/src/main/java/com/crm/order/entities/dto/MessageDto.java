package com.crm.order.entities.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageDto {
    private UUID orderId;
    private String sender; // "admin" or "client"

    @Column(columnDefinition = "TEXT")
    private String text;

    private LocalDateTime timestamp;
}
