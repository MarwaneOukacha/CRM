package com.crm.product.entities.dto.response;

import lombok.Data;
import java.util.UUID;

@Data
public class MaterialResponseDTO {
    private UUID id;
    private String name;
    private String status;
}

