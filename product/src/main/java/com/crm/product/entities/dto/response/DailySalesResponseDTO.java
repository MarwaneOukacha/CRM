package com.crm.product.entities.dto.response;

import com.crm.product.enums.Status;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class DailySalesResponseDTO {
    private UUID id;
    private int days;
    private int percent;
    private Status status;
}
