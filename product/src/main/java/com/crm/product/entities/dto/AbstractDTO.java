package com.crm.product.entities.dto;

import com.crm.product.enums.CategoryStatus;
import com.crm.product.enums.Status;
import lombok.Data;

@Data
public class AbstractDTO {
    private String name;
    private Status status;
}
