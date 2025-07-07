package com.crm.product.mapper;

import com.crm.product.entities.Designer;
import com.crm.product.entities.dto.response.DesignerResponseDTO;
import com.crm.product.entities.dto.response.DesignerUpdateResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DesignerMapper {
    DesignerResponseDTO toResponseDTO(Designer designer);
    DesignerUpdateResponseDTO toUpdateResponseDTO(Designer designer);
}