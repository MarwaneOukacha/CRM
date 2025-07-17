package com.crm.order.entities.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CampaignDiscountRequestDTO {
    private String title;
    private String description;
    private Double discountPercent;
    private LocalDate startDate;
    private LocalDate endDate;
}

