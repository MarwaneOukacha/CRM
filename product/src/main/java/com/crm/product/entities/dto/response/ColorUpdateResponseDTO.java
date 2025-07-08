package com.crm.product.entities.dto.response;

import com.crm.product.entities.dto.AbstractDTO;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class ColorUpdateResponseDTO extends AbstractDTO {
    private UUID id;
    private String color;
}
