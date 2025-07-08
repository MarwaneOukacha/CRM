package com.crm.product.mapper;

import com.crm.product.entities.Occasion;
import com.crm.product.entities.dto.request.OccasionRequestDTO;
import com.crm.product.entities.dto.request.OccasionUpdateRequestDTO;
import com.crm.product.entities.dto.response.OccasionResponseDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface OccasionMapper {

    Occasion toEntity(OccasionRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(OccasionUpdateRequestDTO dto, @MappingTarget Occasion entity);

    OccasionResponseDTO toResponseDTO(Occasion occasion);
}
