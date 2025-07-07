package com.crm.product.mapper;

import com.crm.product.entities.Category;
import com.crm.product.entities.dto.request.CategoryAddRequestDTO;
import com.crm.product.entities.dto.request.CategoryUpdateRequestDTO;
import com.crm.product.entities.dto.response.CategoryResponseDTO;
import com.crm.product.entities.dto.response.CategoryUpdateResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category fromAddDto(CategoryAddRequestDTO dto);

    void updateFromDto(CategoryUpdateRequestDTO dto, @MappingTarget Category category);

    CategoryResponseDTO toResponseDto(Category category);

    CategoryUpdateResponseDTO toCategoryUpdateResponseDTO(Category category);
}

