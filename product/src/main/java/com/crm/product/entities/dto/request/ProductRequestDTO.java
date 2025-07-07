package com.crm.product.entities.dto.request;

import com.crm.product.entities.Category;
import com.crm.product.enums.ProductStatus;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@ToString
public class ProductRequestDTO {
    private String name;
    private String description;
    private Double price;
    private String code;
    private int clicks;
    private int favorite;
    private int cart;
    private double size;
    private String caratId;
    private ProductStatus status;
    private String categoryId;
    private String agencyId;
    private String type;
    private Double weight;
    private String partnerId;
    private List<MediaRequestDTO> media;
}
