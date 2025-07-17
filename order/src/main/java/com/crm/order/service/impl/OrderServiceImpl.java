package com.crm.order.service.impl;

import com.crm.order.dao.OrderDAO;
import com.crm.order.entities.Order;
import com.crm.order.entities.dto.request.OrderRequestDTO;
import com.crm.order.entities.dto.response.OrderResponseDTO;
import com.crm.order.entities.model.SearchCriteria;
import com.crm.order.mapper.OrderMapper;
import com.crm.order.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        log.info("Creating Order for userId: {}", orderRequestDTO.getCustomerCode());
        Order order = orderMapper.toEntity(orderRequestDTO);
        Order savedOrder = orderDAO.save(order);
        log.info("Order created with ID: {}", savedOrder.getId());
        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrderById(UUID id) {
        log.info("Fetching Order by ID: {}", id);
        Order order = orderDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));
        return orderMapper.toDTO(order);
    }

    @Override
    public Page<OrderResponseDTO> getAllOrdersWithCretiria(SearchCriteria SearchCretiria, Pageable pageable) {
        log.info("Fetching all Orders with pagination");
        return orderDAO.findAllWithCriteria(SearchCretiria,pageable).map(orderMapper::toDTO);
    }




}

