package com.crm.product.entities.dto.response;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class ColorResponseDTO {
    private UUID id;
    private String name;
    private String status;
    private String color;
}