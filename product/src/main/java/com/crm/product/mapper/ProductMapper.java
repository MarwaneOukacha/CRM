package com.crm.product.mapper;

import com.crm.product.entities.*;
import com.crm.product.entities.dto.request.MediaRequestDTO;
import com.crm.product.entities.dto.request.ProductRequestDTO;
import com.crm.product.entities.dto.request.ProductUpdateRequestDTO;
import com.crm.product.entities.dto.response.*;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    // From ProductRequestDTO to Product entity
    @Mapping(target = "carat.id", source = "caratId")
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "productOccasion", ignore = true)
    @Mapping(target = "productMaterials", ignore = true)
    @Mapping(target = "productColors", ignore = true)
    @Mapping(target = "productDesigners", ignore = true)
    @Mapping(target = "media", ignore = true) // Will be set in service
    Product fromRequestDto(ProductRequestDTO dto);

    // From ProductUpdateRequestDTO to existing Product entity
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "carat.id", source = "caratId")
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "productOccasion", ignore = true)
    @Mapping(target = "productMaterials", ignore = true)
    @Mapping(target = "productColors", ignore = true)
    @Mapping(target = "productDesigners", ignore = true)
    @Mapping(target = "media", ignore = true) // Will be set in service
    void updateFromDto(ProductUpdateRequestDTO dto, @MappingTarget Product entity);

    // From Product entity to ProductResponseDTO
    @Mapping(source = "carat.id", target = "caratId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(target = "occasionIds", expression = "java(mapOccasionDTOs(product))")
    @Mapping(target = "materialIds", expression = "java(mapMaterialDTOs(product))")
    @Mapping(target = "colorIds", expression = "java(mapColorDTOs(product))")
    @Mapping(target = "designerIds", expression = "java(mapDesignerDTOs(product))")
    ProductResponseDTO toResponseDto(Product product);

    // Helpers

    default List<OccasionResponseDTO> mapOccasionDTOs(Product product) {
        if (product.getProductOccasion() == null) return List.of();
        return product.getProductOccasion().stream()
                .map(po -> {
                    Occasion occasion = po.getOccasion();
                    OccasionResponseDTO dto = new OccasionResponseDTO();
                    dto.setId(occasion.getId());
                    dto.setName(occasion.getName());
                    dto.setStatus(occasion.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    default List<MaterialResponseDTO> mapMaterialDTOs(Product product) {
        if (product.getProductMaterials() == null) return List.of();
        return product.getProductMaterials().stream()
                .map(pm -> {
                    Material material = pm.getMaterial();
                    MaterialResponseDTO dto = new MaterialResponseDTO();
                    dto.setId(material.getId());
                    dto.setName(material.getName());
                    dto.setStatus(material.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    default List<ColorResponseDTO> mapColorDTOs(Product product) {
        if (product.getProductColors() == null) return List.of();
        return product.getProductColors().stream()
                .map(pc -> {
                    Color color = pc.getColor();
                    ColorResponseDTO dto = new ColorResponseDTO();
                    dto.setId(color.getId());
                    dto.setName(color.getName());
                    dto.setStatus(color.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    default List<DesignerResponseDTO> mapDesignerDTOs(Product product) {
        if (product.getProductDesigners() == null) return List.of();
        return product.getProductDesigners().stream()
                .map(pd -> {
                    Designer designer = pd.getDesigner();
                    DesignerResponseDTO dto = new DesignerResponseDTO();
                    dto.setId(designer.getId());
                    dto.setName(designer.getName());
                    dto.setStatus(designer.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    default MediaResponseDTO mapMediaToDTO(Media media) {
        if (media == null) return null;
        MediaResponseDTO dto = new MediaResponseDTO();
        dto.setId(media.getId());
        dto.setName(media.getName());
        dto.setType(media.getType());
        dto.setUrl(media.getUrl());
        return dto;
    }




}
