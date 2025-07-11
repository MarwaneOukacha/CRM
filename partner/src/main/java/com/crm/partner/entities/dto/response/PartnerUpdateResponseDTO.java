package com.crm.partner.entities.dto.response;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;
import java.time.LocalDateTime;
@Data
@ToString
public class PartnerUpdateResponseDTO {
    private UUID id;
    private String status;
    private String companyName;
    private String contactName;
    private String contactEmail;
    private float commissionRate;
    private String contractTerms;

}