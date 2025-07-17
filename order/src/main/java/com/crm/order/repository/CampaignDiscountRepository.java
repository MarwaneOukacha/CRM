package com.crm.order.repository;

import com.crm.order.entities.CampaignDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CampaignDiscountRepository extends JpaRepository<CampaignDiscount, UUID>, JpaSpecificationExecutor<CampaignDiscount> { }
