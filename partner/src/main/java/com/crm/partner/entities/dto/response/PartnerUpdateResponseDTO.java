package com.crm.partner.entities.dto.response;

import com.crm.partner.entities.dto.CompanyDto;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;
import java.time.LocalDateTime;
@Data
@ToString
public class PartnerUpdateResponseDTO {
    private UUID id;
    private String status;
    private String name;
    private String email;
    private String phone;
    private String address;

    // Company Information
    private CompanyDto company;
}