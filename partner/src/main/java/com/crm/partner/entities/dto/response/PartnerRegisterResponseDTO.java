package com.crm.partner.entities.dto.response;

import com.crm.partner.entities.dto.CompanyDto;
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

    // Company Information
    private CompanyDto companyDto;

    private String notes;

}