package com.crm.product.entities.dto.request;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class MediaRequestDTO {
    private String name;
    private String type;           // e.g., "image/png"
}

