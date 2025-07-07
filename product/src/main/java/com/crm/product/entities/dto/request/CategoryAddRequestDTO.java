package com.crm.product.entities.dto.request;

import com.crm.product.enums.CategoryStatus;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CategoryAddRequestDTO {
    private String parent;
    private String name;
    private CategoryStatus status;

}
