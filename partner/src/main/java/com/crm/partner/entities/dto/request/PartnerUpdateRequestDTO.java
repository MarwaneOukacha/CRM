package com.crm.partner.entities.dto.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PartnerUpdateRequestDTO {
    private String companyName;
    private String contactName;
    private String contactEmail;
    private float commissionRate;
    private String contractTerms;
}