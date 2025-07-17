package com.crm.order.repository;

import com.crm.order.entities.ProductSaleHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductSaleHistoryRepository extends JpaRepository<ProductSaleHistory, UUID>, JpaSpecificationExecutor<ProductSaleHistory> { }

