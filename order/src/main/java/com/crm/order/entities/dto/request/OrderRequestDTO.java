package com.crm.order.entities.dto.request;


import com.crm.order.entities.File;
import com.crm.order.enums.OrderType;
import com.crm.order.enums.PaymentType;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class OrderRequestDTO {
    private String customerCode;
    private String customerEmail;
    private OrderType type;
    private String orderCode;
    private PaymentType paymentType;
    private Double totalPrice;
    private Double depositPaid;
    private Double penaltyFee;
    private Double damageFee;
    private Double totalDebt;
    private Boolean contractSent;
    private File file;
    private List<OrderItemRequestDTO> orderItems;
}
