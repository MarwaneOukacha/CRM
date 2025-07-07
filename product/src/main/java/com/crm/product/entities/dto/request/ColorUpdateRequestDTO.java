package com.crm.product.entities.dto.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ColorUpdateRequestDTO {
    private String name;
    private String status;
    private String productId;

}
