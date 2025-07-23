package com.crm.partner.controller;

import com.crm.partner.entities.dto.SearchPartnerCriteria;
import com.crm.partner.entities.dto.request.PartnerRegisterRequestDTO;
import com.crm.partner.entities.dto.request.PartnerUpdateRequestDTO;
import com.crm.partner.entities.dto.response.PartnerProfileResponseDTO;
import com.crm.partner.entities.dto.response.PartnerRegisterResponseDTO;
import com.crm.partner.entities.dto.response.PartnerUpdateResponseDTO;
import com.crm.partner.services.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/partners")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;


    // POST /partners/register
    @PostMapping("/register")
    public ResponseEntity<PartnerRegisterResponseDTO> registerPartner(
            @RequestBody PartnerRegisterRequestDTO request) throws Exception {
        PartnerRegisterResponseDTO response = partnerService.registerPartner(request);
        return ResponseEntity.ok(response);
    }

    // GET /partners/{partnerId}
    @GetMapping("/{partnerId}")
    public PartnerProfileResponseDTO getPartner(
            @PathVariable String partnerId) {
        return partnerService.getPartnerById(partnerId);
    }

    // PUT /partners/{partnerId}
    @PutMapping("/{partnerId}")
    public ResponseEntity<PartnerUpdateResponseDTO> updatePartner(
            @PathVariable String partnerId,
            @RequestBody PartnerUpdateRequestDTO request) {
        PartnerUpdateResponseDTO response = partnerService.updatePartner(partnerId, request);
        return ResponseEntity.ok(response);
    }



    // Optional: search with criteria (GET /partners?companyName=...&status=... etc.)
    @GetMapping
    public ResponseEntity<Page<PartnerProfileResponseDTO>> searchPartners(
            SearchPartnerCriteria criteria,
            Pageable pageable) {
        Page<PartnerProfileResponseDTO> results = partnerService.searchPartners(criteria, pageable);
        return ResponseEntity.ok(results);
    }
}
