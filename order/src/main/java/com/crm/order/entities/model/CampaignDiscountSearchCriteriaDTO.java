package com.crm.order.entities.model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class CampaignDiscountSearchCriteriaDTO {
    private String keyword;
}
