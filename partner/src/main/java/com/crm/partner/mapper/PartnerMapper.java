package com.crm.partner.mapper;

import com.crm.partner.entities.Partner;
import com.crm.partner.entities.dto.request.PartnerRegisterRequestDTO;
import com.crm.partner.entities.dto.request.PartnerUpdateRequestDTO;
import com.crm.partner.entities.dto.response.PartnerProfileResponseDTO;
import com.crm.partner.entities.dto.response.PartnerRegisterResponseDTO;
import com.crm.partner.entities.dto.response.PartnerUpdateResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PartnerMapper {

    PartnerMapper INSTANCE = Mappers.getMapper(PartnerMapper.class);

    // From PartnerRegisterRequestDTO to Partner entity
    Partner toEntity(PartnerRegisterRequestDTO dto);

    // From Partner entity to PartnerRegisterResponseDTO
    @Mapping(source = "status", target = "status")
    PartnerRegisterResponseDTO toRegisterResponseDTO(Partner partner);

    // From Partner entity to PartnerProfileResponseDTO
    PartnerProfileResponseDTO toProfileResponseDTO(Partner partner);

    // From PartnerUpdateRequestDTO to Partner entity (for update)
    void updateEntityFromDto(PartnerUpdateRequestDTO dto, @MappingTarget Partner entity);

    // From Partner entity to PartnerUpdateResponseDTO
    PartnerUpdateResponseDTO toUpdateResponseDTO(Partner partner);
}

