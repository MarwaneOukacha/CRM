package com.crm.product.entities.dto.request;

import com.crm.product.entities.dto.ContractDto;
import com.crm.product.enums.ProductStatus;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductRequestDTO {
    private String name;
    private String description;
    private Double price;
    private String code;
    private ProductStatus raison;
    private int clicks;
    private int favorite;
    private int cart;
    private double size;
    private String caratId;
    private ProductStatus status;
    private double quantity;
    private double rentPrice;
    private String categoryId;
    private List<String> occasionIds;
    private List<String> materialIds;
    private List<String> colorIds;
    private List<String> designerIds;
    private String agencyId;
    private String type;
    private Double weight;
    private String partnerCode;
    private ContractDto contract;
    private MediaRequestDTO media;
}
