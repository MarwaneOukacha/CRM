package com.crm.product.repository;

import com.crm.product.entities.DailySales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DailySalesRepository extends JpaRepository<DailySales, UUID>, JpaSpecificationExecutor<DailySales> {}

