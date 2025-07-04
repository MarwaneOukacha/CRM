package com.crm.product.entities.dto.request;

import lombok.Data;
import java.util.UUID;

@Data
public class MediaRequestDTO {
    private String name;
    private String type;           // e.g., "image/png"
    private String url;            // Where the media is stored
    private UUID productId;        // ID of the related product
}

