package com.crm.order.repository;

import com.crm.order.entities.ProductRentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRentHistoryRepository extends JpaRepository<ProductRentHistory, UUID>, JpaSpecificationExecutor<ProductRentHistory> { }

