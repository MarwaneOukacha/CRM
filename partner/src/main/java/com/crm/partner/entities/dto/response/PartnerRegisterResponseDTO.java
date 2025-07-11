package com.crm.partner.entities.dto.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PartnerRegisterResponseDTO {
    private String id;
    private String status;
    private String companyName;
    private String contactName;
    private String contactEmail;
    private float commissionRate;
    private String contractTerms;

}