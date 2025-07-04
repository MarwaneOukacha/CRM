package com.crm.product.entities.dto.response;

import lombok.Data;
import java.util.UUID;

@Data
public class MediaResponseDTO {
    private UUID id;
    private String name;
    private String type;
    private String url;
    private UUID productId;
}
