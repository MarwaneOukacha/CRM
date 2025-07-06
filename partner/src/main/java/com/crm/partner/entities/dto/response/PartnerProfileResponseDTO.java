package com.crm.partner.entities.dto.response;
import lombok.Data;

import java.util.UUID;

@Data
public class PartnerProfileResponseDTO {
    private UUID partnerId;
    private String companyName;
    private String contactName;
    private String contactEmail;
    private String status;
    private float commissionRate;
    private String contractTerms;

}