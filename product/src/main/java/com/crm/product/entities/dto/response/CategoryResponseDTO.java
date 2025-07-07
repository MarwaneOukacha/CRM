package com.crm.product.entities.dto.response;

import com.crm.product.enums.CategoryStatus;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class CategoryResponseDTO {
    private UUID id;
    private String parent;
    private String name;
    private CategoryStatus status;


}
