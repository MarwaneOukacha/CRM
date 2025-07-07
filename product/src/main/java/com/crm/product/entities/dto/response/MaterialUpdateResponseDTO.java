package com.crm.product.entities.dto.response;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class MaterialUpdateResponseDTO {
    private UUID id;
    private String name;
    private String status;
}