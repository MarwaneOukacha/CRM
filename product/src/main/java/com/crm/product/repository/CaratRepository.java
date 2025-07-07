package com.crm.product.repository;

import com.crm.product.entities.Carat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CaratRepository extends JpaRepository<Carat, UUID>, JpaSpecificationExecutor<Carat> {
}

