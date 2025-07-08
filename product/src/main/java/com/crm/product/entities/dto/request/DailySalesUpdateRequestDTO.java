package com.crm.product.entities.dto.request;

import com.crm.product.enums.Status;
import lombok.Data;

@Data
public class DailySalesUpdateRequestDTO {
    private Integer days;     // Use Integer to allow null checks
    private Integer percent;
    private Status status;
}

