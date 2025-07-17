package com.crm.order.entities.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDiscountResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private Double discountPercent;
    private LocalDate startDate;
    private LocalDate endDate;
}
