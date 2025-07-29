package com.crm.order.entities.dto.response;

import com.crm.order.entities.File;
import com.crm.order.entities.model.OrderStatusHistoryDTO;
import com.crm.order.enums.OrderStatus;
import com.crm.order.enums.OrderType;
import com.crm.order.enums.PaymentType;
import lombok.Data;

import java.util.List;
import java.util.UUID;
@Data
public class OrderResponseDTO {
    private UUID id;
    private String customerCode;
    private String customerEmail;
    private String telegramName;
    private OrderType type;
    private OrderStatus status;
    private String orderCode;
    private PaymentType paymentType;
    private Double totalPrice;
    private Double depositPaid;
    private Double penaltyFee;
    private Double damageFee;
    private Double totalDebt;
    private Boolean contractSent;
    private File file;
    private List<OrderItemResponseDTO> orderItems;
    private List<OrderStatusHistoryDTO> statusHistory;
}
