package com.crm.order.repository;

import com.crm.order.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByOrderIdOrderByTimestampAsc(UUID orderId);
}
