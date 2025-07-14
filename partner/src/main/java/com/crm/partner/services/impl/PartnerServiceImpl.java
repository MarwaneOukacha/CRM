package com.crm.partner.services.impl;

import com.crm.partner.dao.PartnerDao;
import com.crm.partner.entities.Company;
import com.crm.partner.entities.Partner;
import com.crm.partner.entities.PartnerContract;
import com.crm.partner.entities.dto.SearchPartnerCriteria;
import com.crm.partner.entities.dto.request.PartnerRegisterRequestDTO;
import com.crm.partner.entities.dto.request.PartnerUpdateRequestDTO;
import com.crm.partner.entities.dto.response.PartnerProfileResponseDTO;
import com.crm.partner.entities.dto.response.PartnerRegisterResponseDTO;
import com.crm.partner.entities.dto.response.PartnerUpdateResponseDTO;
import com.crm.partner.enums.ContractStatus;
import com.crm.partner.enums.PartnerStatus;
import com.crm.partner.mapper.PartnerMapper;
import com.crm.partner.repository.CompanyRepository;
import com.crm.partner.repository.ContractRepository;
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
    private final PartnerMapper partnerMapper;
    private final ContractRepository contractRepository;
    private final CompanyRepository companyRepository;


    @Override
    @Transactional
    public PartnerRegisterResponseDTO registerPartner(PartnerRegisterRequestDTO request) {
        // 1. Map DTO to Partner entity
        Partner partner = partnerMapper.toEntity(request);
        partner.setStatus(PartnerStatus.PENDING);

        // 2. Save the partner first to generate ID
        Partner savedPartner = partnerDao.save(partner);

        // 3. Link partner to contracts (which are inside the company)
        Company company = partner.getCompany();
        for (PartnerContract contract : partner.getContracts()) {
            contract.setPartner(savedPartner);
            contract.setStatus(ContractStatus.ACTIVE);
            contractRepository.save(contract);
        }

        // 4. Save company
        companyRepository.save(company);

        // 5. Link saved company back to partner and update
        savedPartner.setCompany(company);
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
        if (request.getCompany() != null && !request.getPhone().isEmpty()) {
            if(request.getCompany().getName()!= null && !request.getCompany().getName().isEmpty()) partner.getCompany().setName(request.getCompany().getName());
            if(request.getCompany().getAddress()!= null && !request.getCompany().getAddress().isEmpty()) partner.getCompany().setAddress(request.getCompany().getAddress());
            if(request.getCompany().getTaxId()!= null && !request.getCompany().getTaxId().isEmpty()) partner.getCompany().setTaxId(request.getCompany().getTaxId());
            if(request.getCompany().getContactEmail()!= null && !request.getCompany().getContactEmail().isEmpty()) partner.getCompany().setContactEmail(request.getCompany().getContactEmail());
            if(request.getCompany().getContactPhone()!= null && !request.getCompany().getContactPhone().isEmpty()) partner.getCompany().setContactPhone(request.getCompany().getContactPhone());
        }


        Partner updated = partnerDao.save(partner);

        return partnerMapper.toUpdateResponseDTO(updated);
    }

    @Override
    public Page<PartnerProfileResponseDTO> searchPartners(SearchPartnerCriteria criteria, Pageable pageable) {
        Page<Partner> page = partnerDao.findAllWithCriteria(criteria, pageable);
        return page.map(partnerMapper::toProfileResponseDTO);
    }
}
