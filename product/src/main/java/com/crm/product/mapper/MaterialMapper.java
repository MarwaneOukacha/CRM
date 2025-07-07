package com.crm.product.mapper;

import com.crm.product.entities.Material;
import com.crm.product.entities.dto.request.MaterialRequestDTO;
import com.crm.product.entities.dto.request.MaterialUpdateRequestDTO;
import com.crm.product.entities.dto.response.MaterialResponseDTO;
import com.crm.product.entities.dto.response.MaterialUpdateResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaterialMapper {
    Material fromUpdateDto(MaterialUpdateRequestDTO dto);
    MaterialResponseDTO toResponseDto(Material material);
    MaterialUpdateResponseDTO toUpdateResponseDto(Material material);

    MaterialResponseDTO toMaterialResponseDTO(Material save);

    Material toResponse(MaterialRequestDTO material);

}

