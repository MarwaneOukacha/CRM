package com.crm.partner.entities.dto.response;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Data
@ToString
public class PayoutResponseDTO {
    private UUID payoutId;
    private LocalDate payoutPeriodStart;
    private LocalDate payoutPeriodEnd;
    private float amount;
    private String status;

}