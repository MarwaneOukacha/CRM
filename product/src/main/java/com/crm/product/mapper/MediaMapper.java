package com.crm.product.mapper;

import com.crm.product.entities.Media;
import com.crm.product.entities.dto.request.MediaRequestDTO;
import com.crm.product.entities.dto.response.MediaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    Media toEntity(MediaRequestDTO dto);
    void updateEntityFromDto(MediaRequestDTO dto, @MappingTarget Media entity);

    MediaResponseDTO toResponseDTO(Media entity);
}
