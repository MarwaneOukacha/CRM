package com.crm.order.entities.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
public class SearchCriteria {
    private String keyword;
}
