package com.crm.partner.entities.dto;

import lombok.Data;

@Data
public class SearchPartnerCriteria {
    private String companyName;
    private String contactEmail;
    private String status; // 'pending', 'verified', 'rejected'
    private String keyword;
}