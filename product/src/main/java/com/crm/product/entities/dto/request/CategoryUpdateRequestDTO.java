package com.crm.product.entities.dto.request;

import com.crm.product.entities.dto.AbstractDTO;
import com.crm.product.enums.CategoryStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CategoryUpdateRequestDTO extends AbstractDTO {
    private String parent;

}
