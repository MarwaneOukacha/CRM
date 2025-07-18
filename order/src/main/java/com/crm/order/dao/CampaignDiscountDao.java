package com.crm.order.dao;

import com.crm.order.entities.CampaignDiscount;
import com.crm.order.entities.model.CampaignDiscountSearchCriteriaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CampaignDiscountDao {

    CampaignDiscount save(CampaignDiscount campaignDiscount);

    CampaignDiscount update(CampaignDiscount campaignDiscount);

    void deleteById(UUID id);

    Optional<CampaignDiscount> findById(UUID id);

    Page<CampaignDiscount> search(CampaignDiscountSearchCriteriaDTO criteria, Pageable pageable);

}
