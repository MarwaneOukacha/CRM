package com.crm.product.mapper;

import com.crm.product.entities.Carat;
import com.crm.product.entities.dto.response.CaratResponseDTO;
import com.crm.product.entities.dto.response.CaratUpdateResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CaratMapper {
    CaratResponseDTO toResponseDTO(Carat carat);
    CaratUpdateResponseDTO toUpdateResponseDTO(Carat carat);
}
