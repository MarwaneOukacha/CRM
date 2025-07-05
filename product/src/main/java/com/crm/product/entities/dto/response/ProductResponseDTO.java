package com.crm.product.entities.dto.response;

import com.crm.product.entities.Media;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProductResponseDTO {
    private String id;
    private String name;
    private String description;
    private String material;
    private Double price;
    private String category;
    private String status;
    private Double stockQuantity;
    private List<MediaResponseDTO> media;
    private Map<String, String> characteristics;
}
