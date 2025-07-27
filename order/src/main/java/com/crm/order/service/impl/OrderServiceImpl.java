package com.crm.order.service.impl;

import com.crm.order.dao.OrderDAO;
import com.crm.order.entities.File;
import com.crm.order.entities.Order;
import com.crm.order.entities.OrderStatusHistory;
import com.crm.order.entities.dto.request.OrderItemRequestDTO;
import com.crm.order.entities.dto.request.OrderRequestDTO;
import com.crm.order.entities.dto.request.ProductUpdateRequestDTO;
import com.crm.order.entities.dto.response.OrderResponseDTO;
import com.crm.order.entities.model.OrderStatusHistoryDTO;
import com.crm.order.entities.model.SearchCriteria;
import com.crm.order.enums.OrderStatus;
import com.crm.order.mapper.OrderMapper;
import com.crm.order.repository.FileRepository;
import com.crm.order.repository.OrderItemRepository;
import com.crm.order.repository.OrderStatusHistoryRepository;
import com.crm.order.service.OrderService;
import com.crm.order.service.external.ProductService;
import com.crm.order.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final OrderMapper orderMapper;
    private final FileRepository fileRepo;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final ProductService productService;
    @Value("${app.file-url}")
    private String uploadDir;

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) throws Exception {
        log.info("Creating Order for customerCode: {}", orderRequestDTO.getCustomerCode());

        Order order = orderMapper.toEntity(orderRequestDTO);
        order.setOrderCode(orderRequestDTO.getOrderCode());
        order.setStatus(OrderStatus.CONFIRMED);

        File file = orderRequestDTO.getFile();
        if (file == null) {
            throw new RuntimeException("Signed contract file is required");
        }

        String originalName = file.getName(); // e.g. "mar.pdf"
        String nameWithoutExt = originalName.contains(".")
                ? originalName.substring(0, originalName.lastIndexOf('.'))
                : originalName;

        file.setUrl(uploadDir +nameWithoutExt+ java.io.File.separator +file.getName());
        File savedFile = fileRepo.save(file);
        order.setFile(savedFile);


        Order savedOrder = orderDAO.save(order);

        savedOrder.getOrderItems().forEach(orderItem -> {
            orderItem.setOrder(savedOrder);
            orderItemRepository.save(orderItem);
        });
        OrderStatusHistory orderStatusHistory=new OrderStatusHistory();
        orderStatusHistory.setOrder(savedOrder);
        orderStatusHistory.setStatus(savedOrder.getStatus());
        orderStatusHistoryRepository.save(orderStatusHistory);


        savedOrder.getOrderItems().forEach(orderItemRequestDTO -> {
            ProductUpdateRequestDTO productUpdateRequestDTO=new ProductUpdateRequestDTO();
            if(orderItemRequestDTO.getQuantity()!=null && orderItemRequestDTO.getRentalStartDate()!=null && orderItemRequestDTO.getRentalEndDate()!=null){
                productUpdateRequestDTO.setQuantityRented(orderItemRequestDTO.getQuantity());
                productService.update(orderItemRequestDTO.getProductId(),productUpdateRequestDTO);
            }else if(orderItemRequestDTO.getQuantity()!=null){
                productUpdateRequestDTO.setQuantitySaled(orderItemRequestDTO.getQuantity());
                productService.update(orderItemRequestDTO.getProductId(),productUpdateRequestDTO);
            }



        });



        log.info("Order created with ID: {}", savedOrder.getId());
        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrderById(UUID id) {
        log.info("Fetching Order by ID: {}", id);

        Order order = orderDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + id));

        List<OrderStatusHistory> statusHistories = orderStatusHistoryRepository.findByOrder(order);

        OrderResponseDTO dto = orderMapper.toDTO(order);

        List<OrderStatusHistoryDTO> statusHistoryDTOs = statusHistories.stream()
                .map(history -> {
                    OrderStatusHistoryDTO dtoHistory = new OrderStatusHistoryDTO();
                    dtoHistory.setId(history.getId());
                    dtoHistory.setStatus(history.getStatus());
                    dtoHistory.setCreated(history.getCreated());
                    return dtoHistory;
                })
                .collect(Collectors.toList());

        dto.setStatusHistory(statusHistoryDTOs);

        return dto;
    }


    @Override
    public Page<OrderResponseDTO> getAllOrdersWithCretiria(SearchCriteria SearchCretiria, Pageable pageable) {
        log.info("Fetching all Orders with pagination");
        return orderDAO.findAllWithCriteria(SearchCretiria,pageable).map(orderMapper::toDTO);
    }




}

