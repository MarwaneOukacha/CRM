package com.crm.product.entities.dto.response;

import com.crm.product.entities.dto.AbstractDTO;
import com.crm.product.enums.CategoryStatus;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class CategoryUpdateResponseDTO extends AbstractDTO {
    private UUID id;
    private String parent;


}
