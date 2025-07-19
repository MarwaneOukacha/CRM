package com.crm.order.repository;

import com.crm.order.entities.Order;
import com.crm.order.entities.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, UUID>, JpaSpecificationExecutor<OrderStatusHistory> {
    List<OrderStatusHistory> findByOrder(Order order);

}
