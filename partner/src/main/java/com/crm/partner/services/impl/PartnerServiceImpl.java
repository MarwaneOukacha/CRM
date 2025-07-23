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
import com.crm.partner.utils.PartnerCodeGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerDao partnerDao;
    private final PartnerMapper partnerMapper;


    @Override
    @Transactional
    public PartnerRegisterResponseDTO registerPartner(PartnerRegisterRequestDTO request) throws Exception {
        // 1. Map DTO to Partner entity
        Partner partner = partnerMapper.toEntity(request);
        partner.setStatus(PartnerStatus.VERIFIED);
        String code = PartnerCodeGenerator.generatePartnerCode();
        partner.setCode(code);
        // 2. Save the partner first to generate ID
        Partner savedPartner = partnerDao.save(partner);

        savedPartner = partnerDao.save(savedPartner);

        // 6. Map to response
        return partnerMapper.toRegisterResponseDTO(savedPartner);
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
        if (request.getName() != null && !request.getName().isEmpty()) partner.setName(request.getName());
        if (request.getAddress() != null && !request.getAddress().isEmpty()) partner.setAddress(request.getAddress());
        if (request.getEmail() != null && !request.getEmail().isEmpty()) partner.setEmail(request.getEmail());
        if (request.getPhone() != null && !request.getPhone().isEmpty()) partner.setPhone(request.getPhone());
        if (request.getPassportSeries() != null && !request.getPassportSeries().isEmpty()) partner.setPassportSeries(request.getPassportSeries());
        if (request.getPassportNumber() != null && !request.getPassportNumber().isEmpty()) partner.setPassportNumber(request.getPassportNumber());
        if (request.getBankTIN() != null && !request.getBankTIN().isEmpty()) partner.setBankTIN(request.getBankTIN());
        if (request.getBankSwiftCode() != null && !request.getBankSwiftCode().isEmpty()) partner.setBankSwiftCode(request.getBankSwiftCode());
        if (request.getBankAccountNumber() != null && !request.getBankAccountNumber().isEmpty()) partner.setBankAccountNumber(request.getBankAccountNumber());
        if (request.getReceivingBankName() != null && !request.getReceivingBankName().isEmpty()) partner.setReceivingBankName(request.getReceivingBankName());
        if (request.getReceivingBankCurrency() != null && !request.getReceivingBankCurrency().isEmpty()) partner.setReceivingBankCurrency(request.getReceivingBankCurrency());
        if (request.getCompanyName() != null && !request.getCompanyName().isEmpty()) partner.setCompanyName(request.getCompanyName());

        Partner updated = partnerDao.save(partner);

        return partnerMapper.toUpdateResponseDTO(updated);
    }

    @Override
    public Page<PartnerProfileResponseDTO> searchPartners(SearchPartnerCriteria criteria, Pageable pageable) {
        Page<Partner> page = partnerDao.findAllWithCriteria(criteria, pageable);
        return page.map(partnerMapper::toProfileResponseDTO);
    }
}
