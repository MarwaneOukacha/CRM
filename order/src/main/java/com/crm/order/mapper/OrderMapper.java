package com.crm.order.mapper;

import com.crm.order.entities.Order;
import com.crm.order.entities.dto.request.OrderRequestDTO;
import com.crm.order.entities.dto.response.OrderResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderRequestDTO dto);
    OrderResponseDTO toDTO(Order entity);

    List<OrderResponseDTO> toDTOList(List<Order> entities);
}