package com.crm.order.service.impl;

import com.crm.order.dao.CampaignDiscountDao;
import com.crm.order.entities.CampaignDiscount;
import com.crm.order.entities.model.CampaignDiscountDTO;
import com.crm.order.entities.model.CampaignDiscountSearchCriteriaDTO;
import com.crm.order.entities.model.SearchCriteria;
import com.crm.order.mapper.CampaignDiscountMapper;
import com.crm.order.service.CampaignDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CampaignDiscountServiceImpl implements CampaignDiscountService {

    private final CampaignDiscountDao campaignDiscountDao;
    private final CampaignDiscountMapper campaignDiscountMapper;

    @Override
    public CampaignDiscountDTO save(CampaignDiscountDTO campaignDiscountDTO) {
        CampaignDiscount entity = campaignDiscountMapper.toEntity(campaignDiscountDTO);
        CampaignDiscount saved = campaignDiscountDao.save(entity);
        return campaignDiscountMapper.toDto(saved);
    }

    @Override
    public CampaignDiscountDTO findById(String id) {
        CampaignDiscount entity = campaignDiscountDao.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("CampaignDiscount not found with id: " + id));
        return campaignDiscountMapper.toDto(entity);
    }

    @Override
    public CampaignDiscountDTO deleteById(String id) {
        CampaignDiscount entity = campaignDiscountDao.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("CampaignDiscount not found with id: " + id));
        campaignDiscountDao.deleteById(entity.getId());
        return campaignDiscountMapper.toDto(entity);
    }

    @Override
    public Page<CampaignDiscountDTO> search(CampaignDiscountSearchCriteriaDTO CampaignDiscountSearchCriteriaDTO, Pageable pageable) {
        return campaignDiscountDao.search(CampaignDiscountSearchCriteriaDTO, pageable)
                .map(campaignDiscountMapper::toDto);
    }



    @Override
    public CampaignDiscountDTO update(String id,CampaignDiscountDTO campaignDiscountDTO) {

        CampaignDiscount update = campaignDiscountDao.update(id, campaignDiscountDTO);

        return campaignDiscountMapper.toDto(update);
    }
}
