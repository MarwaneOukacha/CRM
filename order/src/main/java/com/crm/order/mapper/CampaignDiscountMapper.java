package com.crm.order.mapper;

import com.crm.order.entities.CampaignDiscount;
import com.crm.order.entities.model.CampaignDiscountDTO;
import com.crm.order.entities.model.CampaignDiscountSearchCriteriaDTO;
import com.crm.order.entities.model.SearchCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CampaignDiscountMapper {

    CampaignDiscountMapper INSTANCE = Mappers.getMapper(CampaignDiscountMapper.class);

    CampaignDiscount toEntity(CampaignDiscountDTO dto);

    CampaignDiscountDTO toDto(CampaignDiscount entity);


}

