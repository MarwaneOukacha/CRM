package com.crm.product.entities.dto.response;

import com.crm.product.entities.Media;
import com.crm.product.entities.dto.request.MediaRequestDTO;
import com.crm.product.enums.ProductStatus;
import com.crm.product.enums.Status;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private String code;
    private ProductStatus raison;
    private int clicks;
    private int favorite;
    private int cart;
    private double size;
    private UUID caratId;
    private ProductStatus status;
    private UUID categoryId;
    private List<OccasionResponseDTO> occasionIds;
    private List<MaterialResponseDTO> materialIds;
    private List<ColorResponseDTO> colorIds;
    private List<DesignerResponseDTO> designerIds;
    private String agencyId;
    private String type;
    private Double weight;
    private UUID partnerId;
    private List<MediaResponseDTO> media;
    private String barcodeImage;
}