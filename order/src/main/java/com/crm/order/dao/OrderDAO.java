package com.crm.order.dao;

import com.crm.order.entities.Order;
import com.crm.order.entities.model.SearchCriteria;
import com.crm.order.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface OrderDAO {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    Page<Order> findAllWithCriteria(SearchCriteria searchCriteria, Pageable pageable);
    //void deleteById(Long id);
}
