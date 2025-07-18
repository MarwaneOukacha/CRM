package com.crm.order.service;

import com.crm.order.entities.model.CampaignDiscountDTO;
import com.crm.order.entities.model.CampaignDiscountSearchCriteriaDTO;
import com.crm.order.entities.model.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CampaignDiscountService {
    CampaignDiscountDTO save(CampaignDiscountDTO campaignDiscountDTO);
    CampaignDiscountDTO findById(String id);
    CampaignDiscountDTO deleteById(String id);
    Page<CampaignDiscountDTO> search(CampaignDiscountSearchCriteriaDTO searchCriteria, Pageable pageable);
}
