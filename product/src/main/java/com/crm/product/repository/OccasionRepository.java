package com.crm.product.repository;

import com.crm.product.entities.Occasion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OccasionRepository extends JpaRepository<Occasion, UUID>, JpaSpecificationExecutor<Occasion> {
}
