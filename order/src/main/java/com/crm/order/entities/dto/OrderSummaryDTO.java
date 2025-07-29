package com.crm.order.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderSummaryDTO {
    private String orderId;
    private String telegramName;
    private String customerCode;
    private String customerEmail;
    private String orderCode;
}
