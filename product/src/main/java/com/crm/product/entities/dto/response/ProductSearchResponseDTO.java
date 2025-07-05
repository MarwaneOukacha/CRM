package com.crm.product.entities.dto.response;

import com.crm.product.entities.Media;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ProductSearchResponseDTO {
    private String id;
    private String name;
    private String material;
    private Double price;
    private String status;
    private Double stockQuantity;
    private List<MediaResponseDTO> media;
    private Map<String, String> characteristics = new HashMap<>();
}
