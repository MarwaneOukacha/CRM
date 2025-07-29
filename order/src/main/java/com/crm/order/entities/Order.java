package com.crm.order.entities;

import com.crm.order.entities.model.AbstractEntity;
import com.crm.order.enums.OrderStatus;
import com.crm.order.enums.OrderType;
import com.crm.order.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "orders")
@AttributeOverride(name = "id", column = @Column(name = "order_id"))
@Data
public class Order extends AbstractEntity {


    private String customerCode;
    private String customerEmail;
    private String telegramName;
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String orderCode;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private Double totalPrice;
    private Double depositPaid;
    private Double penaltyFee;
    private Double damageFee;
    private Double totalDebt;

    private Boolean contractSent;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderStatusHistory> statusHistories;
    @OneToOne
    private File file;

}
