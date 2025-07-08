package com.crm.product.entities.dto.request;

import com.crm.product.enums.Status;
import lombok.Data;
@Data
public class OccasionUpdateRequestDTO {
    private String name;
    private Status status;
}