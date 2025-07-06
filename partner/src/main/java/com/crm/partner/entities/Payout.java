package com.crm.partner.entities;

import com.crm.partner.entities.model.AbstractEntity;
import com.crm.partner.enums.PayoutStatus;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payouts")
public class Payout extends AbstractEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    private LocalDate payoutPeriodStart;

    private LocalDate payoutPeriodEnd;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PayoutStatus status;


}
