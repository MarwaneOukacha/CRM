package com.crm.partner.services;

import com.crm.partner.entities.Partner;
import com.crm.partner.entities.dto.SearchPartnerCriteria;
import com.crm.partner.entities.dto.request.PartnerRegisterRequestDTO;
import com.crm.partner.entities.dto.request.PartnerUpdateRequestDTO;
import com.crm.partner.entities.dto.response.PartnerProfileResponseDTO;
import com.crm.partner.entities.dto.response.PartnerRegisterResponseDTO;
import com.crm.partner.entities.dto.response.PartnerUpdateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface PartnerService {

    PartnerRegisterResponseDTO registerPartner(PartnerRegisterRequestDTO request) throws Exception;

    PartnerProfileResponseDTO getPartnerById(String partnerId);

    PartnerUpdateResponseDTO updatePartner(String partnerId, PartnerUpdateRequestDTO request);

    Page<PartnerProfileResponseDTO> searchPartners(SearchPartnerCriteria criteria, Pageable pageable);

    PartnerProfileResponseDTO getPartnerByCode(String partnerCode);

}
