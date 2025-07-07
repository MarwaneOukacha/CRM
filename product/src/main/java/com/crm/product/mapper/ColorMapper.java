package com.crm.product.mapper;

import com.crm.product.entities.Color;
import com.crm.product.entities.dto.request.ColorRequestDTO;
import com.crm.product.entities.dto.response.ColorResponseDTO;
import com.crm.product.entities.dto.response.ColorUpdateResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    ColorResponseDTO toResponseDTO(Color color);
    ColorUpdateResponseDTO toUpdateResponseDTO(Color color);

    Color toColor(ColorRequestDTO color);

}
