package com.crm.product.entities.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SearchCategoryCriteria {
    private String name;
    private String parent;
    private String status;

}

