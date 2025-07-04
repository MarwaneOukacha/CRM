package com.crm.product.mapper;

import com.crm.product.entities.Product;
import com.crm.product.entities.dto.request.AddProductRequestDTO;
import com.crm.product.entities.dto.response.ProductResponseDTO;
import com.crm.product.entities.dto.response.ProductSearchResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(AddProductRequestDTO dto);

    ProductResponseDTO toResponseDTO(Product product);
    void updateEntityFromDto(AddProductRequestDTO dto, @MappingTarget Product entity);

    ProductSearchResponseDTO toSearchResponseDTO(Product product);
}
