package com.crm.order.entities.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Data
@ToString
public class CampaignDiscountDTO {
    private UUID id;
    private String title;
    private String description;
    private Double discountPercent;
    private LocalDate startDate;
    private LocalDate endDate;
}