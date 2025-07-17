package com.crm.order.dao.impl;

import com.crm.order.dao.OrderDAO;
import com.crm.order.entities.Order;
import com.crm.order.entities.model.SearchCriteria;
import com.crm.order.enums.OrderStatus;
import com.crm.order.repository.OrderRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
@Slf4j
public class OrderDAOImpl implements OrderDAO {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderDAOImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        log.info("Saving Order with ID: {}", order.getId());
        Order savedOrder = orderRepository.save(order);
        log.debug("Order saved successfully: {}", savedOrder);
        return savedOrder;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        log.info("Fetching Order by ID: {}", id);
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            log.debug("Order found: {}", order.get());
        } else {
            log.warn("Order not found for ID: {}", id);
        }
        return order;
    }


    @Override
    public Page<Order> findAllWithCriteria(SearchCriteria criteria, Pageable pageable) {

        log.info("ProductDaoImpl::findAllWithCriteria - Searching products with criteria: {}", criteria);
        Specification<Order> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
                String kw = "%" + criteria.getKeyword().toLowerCase() + "%";
                Predicate userPredicate = cb.like(cb.lower(root.get("userCode")), kw);
                Predicate userEmail = cb.like(cb.lower(root.get("userEmail")), kw);
                Predicate status = cb.like(cb.lower(root.get("status")), kw);
                Predicate paymentType = cb.like(cb.lower(root.get("paymentType")), kw);
                Predicate type = cb.like(cb.lower(root.get("type")), kw);
                predicates.add(cb.or(userPredicate, userEmail,status,paymentType,type));
            }



            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Order> result = orderRepository.findAll(specification, pageable);
        log.info("ProductDaoImpl::findAllWithCriteria - Found {} products", result.getTotalElements());
        return result;
    }


}
