package com.crm.order.controller;


import com.crm.order.entities.Message;
import com.crm.order.entities.Order;
import com.crm.order.entities.dto.MessageDto;
import com.crm.order.entities.dto.OrderSummaryDTO;
import com.crm.order.mapper.MessageMapper;
import com.crm.order.repository.MessageRepository;
import com.crm.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired private MessageRepository messageRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired
    private MessageMapper messageMapper;


    @GetMapping("/{orderId}")
    public List<MessageDto> getMessages(@PathVariable String orderId) {
        List<Message> messages = messageRepository.findByOrderIdOrderByTimestampAsc(UUID.fromString(orderId));

        return messages.stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }


    @PostMapping("/{orderId}")
    public ResponseEntity<?> sendMessage(@PathVariable String orderId, @RequestBody Message input) {
        Order order = orderRepository.findById(UUID.fromString(orderId)).orElseThrow(() -> new IllegalArgumentException("Order not found"));

        input.setOrder(order);
        input.setTimestamp(LocalDateTime.now());

        Message saved = messageRepository.save(input);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/orders-with-messages")
    public List<OrderSummaryDTO> getOrdersWithMessages() {
        List<Order> orders = orderRepository.findAll(); // You can optimize this if needed

        return orders.stream()
                .filter(order -> !messageRepository.findByOrderIdOrderByTimestampAsc(order.getId()).isEmpty())
                .map(order -> new OrderSummaryDTO(
                        String.valueOf(order.getId()),
                        order.getTelegramName(),
                        order.getCustomerCode(),
                        order.getCustomerEmail(),
                        order.getOrderCode()
                ))
                .collect(Collectors.toList());
    }

}

