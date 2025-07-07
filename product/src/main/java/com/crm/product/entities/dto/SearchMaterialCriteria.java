package com.crm.product.entities.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SearchMaterialCriteria {
    private String name;
    private String status;

}
