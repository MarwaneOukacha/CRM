package com.crm.partner.entities.dto.response;

import com.crm.partner.entities.dto.CompanyDto;
import com.crm.partner.enums.PartnerStatus;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class PartnerRegisterResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private PartnerStatus status;

    // Company Information
    private CompanyDto company;



}