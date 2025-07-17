package com.crm.product.entities.dto.request;

import com.crm.product.enums.ProductStatus;
import com.crm.product.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdateRequestDTO {
    private String name;
    private String description;
    private Double price;
    private String code;
    private double quantity;
    private double quantityRented;
    private double quantitySaled;
    private double rentPrice;
    private Integer clicks;
    private ProductStatus raison;
    private Integer favorite;
    private Integer cart;
    private Double size;
    private UUID caratId;
    private ProductStatus status;
    private UUID categoryId;
    private List<String> occasionIds;
    private List<String> materialIds;
    private List<String> colorIds;
    private List<String> designerIds;
    private String agencyId;
    private String type;
    private Double weight;
    private String partnerId;
    private List<MediaRequestDTO> media;
}