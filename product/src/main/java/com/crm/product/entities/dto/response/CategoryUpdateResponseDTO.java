package com.crm.product.entities.dto.response;

import com.crm.product.enums.CategoryStatus;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class CategoryUpdateResponseDTO {
    private UUID id;
    private String name;
    private String parent;
    private CategoryStatus status;


}
