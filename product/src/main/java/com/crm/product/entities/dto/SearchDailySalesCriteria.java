package com.crm.product.entities.dto;

import com.crm.product.enums.Status;
import lombok.Data;

@Data
public class SearchDailySalesCriteria {
    private int days;
    private int percent;
    private Status status;
}