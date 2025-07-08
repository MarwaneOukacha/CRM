package com.crm.product.entities.dto.request;

import com.crm.product.entities.dto.AbstractDTO;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ColorUpdateRequestDTO extends AbstractDTO {
    private String color;

}
