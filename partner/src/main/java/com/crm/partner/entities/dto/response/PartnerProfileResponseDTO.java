package com.crm.partner.entities.dto.response;
import com.crm.partner.entities.dto.CompanyDto;
import lombok.Data;

import java.util.UUID;

@Data
public class PartnerProfileResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String address;

    // Company Information
    private CompanyDto company;



}