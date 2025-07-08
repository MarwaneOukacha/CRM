package com.crm.product.mapper;

import com.crm.product.entities.DailySales;
import com.crm.product.entities.dto.response.DailySalesResponseDTO;
import com.crm.product.entities.dto.response.DailySalesUpdateResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DailySalesMapper {
    DailySalesResponseDTO toResponseDTO(DailySales dailySales);
    DailySalesUpdateResponseDTO toUpdateResponseDTO(DailySales dailySales);
}
