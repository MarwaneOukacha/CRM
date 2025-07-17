package com.crm.order.service;

import com.crm.order.entities.dto.request.OrderRequestDTO;
import com.crm.order.entities.dto.response.OrderResponseDTO;
import com.crm.order.entities.model.SearchCriteria;
import com.crm.order.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
    OrderResponseDTO getOrderById(UUID id);
    Page<OrderResponseDTO> getAllOrdersWithCretiria(SearchCriteria SearchCretiria, Pageable pageable);

}
