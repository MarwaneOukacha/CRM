package com.crm.partner.services.impl;

import com.crm.partner.dao.PartnerDao;
import com.crm.partner.entities.Partner;
import com.crm.partner.entities.dto.SearchPartnerCriteria;
import com.crm.partner.entities.dto.request.PartnerRegisterRequestDTO;
import com.crm.partner.entities.dto.request.PartnerUpdateRequestDTO;
import com.crm.partner.entities.dto.response.PartnerProfileResponseDTO;
import com.crm.partner.entities.dto.response.PartnerRegisterResponseDTO;
import com.crm.partner.entities.dto.response.PartnerUpdateResponseDTO;
import com.crm.partner.enums.PartnerStatus;
import com.crm.partner.mapper.PartnerMapper;
import com.crm.partner.services.PartnerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerDao partnerDao;
    private final PartnerMapper partnerMapper; // Assume MapStruct or manual mapper


    @Override
    public PartnerRegisterResponseDTO registerPartner(PartnerRegisterRequestDTO request) {
        // Map DTO to entity
        Partner partner = partnerMapper.toEntity(request);
        partner.setStatus(PartnerStatus.PENDING); // default status
        Partner saved = partnerDao.save(partner);

        return partnerMapper.toRegisterResponseDTO(saved);
    }

    @Override
    public PartnerProfileResponseDTO getPartnerById(String partnerId) {
        Partner byId = partnerDao.findById(partnerId).orElseThrow(() -> {
            return new EntityNotFoundException("Partner not found with ID: " + partnerId);
        });


        return partnerMapper.toProfileResponseDTO(byId);
    }

    @Override
    public PartnerUpdateResponseDTO updatePartner(String partnerId, PartnerUpdateRequestDTO request) {
        Partner partner = partnerDao.findById(String.valueOf(partnerId))
                .orElseThrow(() -> new RuntimeException("Partner not found"));

        // Update fields if present (example with null checks)
        if (request.getCompanyName() != null) partner.setCompanyName(request.getCompanyName());
        if (request.getContactName() != null) partner.setContactName(request.getContactName());
        if (request.getContactEmail() != null) partner.setContactEmail(request.getContactEmail());
        if (request.getCommissionRate() != 0) partner.setCommissionRate(request.getCommissionRate());
        if (request.getContractTerms() != null) partner.setContractTerms(request.getContractTerms());


        Partner updated = partnerDao.save(partner);

        return partnerMapper.toUpdateResponseDTO(updated);
    }

    @Override
    public Page<PartnerProfileResponseDTO> searchPartners(SearchPartnerCriteria criteria, Pageable pageable) {
        Page<Partner> page = partnerDao.findAllWithCriteria(criteria, pageable);
        return page.map(partnerMapper::toProfileResponseDTO);
    }
}
