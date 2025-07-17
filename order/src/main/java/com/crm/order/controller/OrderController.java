package com.crm.order.controller;

import com.crm.order.entities.dto.request.OrderRequestDTO;
import com.crm.order.entities.dto.response.OrderResponseDTO;
import com.crm.order.entities.model.SearchCriteria;
import com.crm.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        log.info("API - Create Order called for customerCode: {}", orderRequestDTO.getCustomerCode());
        OrderResponseDTO createdOrder = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable String id) {
        log.info("API - Get Order by ID: {}", id);
        OrderResponseDTO order = orderService.getOrderById(UUID.fromString(id));
        return ResponseEntity.ok(order);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OrderResponseDTO>> searchOrders(
            SearchCriteria searchCriteria,
            Pageable pageable
    ) {
        log.info("API - Search Orders with criteria: {}", searchCriteria);
        Page<OrderResponseDTO> allOrdersWithCretiria = orderService.getAllOrdersWithCretiria(searchCriteria, pageable);
        return ResponseEntity.ok(allOrdersWithCretiria);
    }
}
