package com.crm.product.entities.dto.request;

import com.crm.product.enums.Status;
import lombok.Data;

@Data
public class DailySalesRequestDTO {
    private int days;
    private int percent;
    private Status status;
}

