package com.crm.partner.entities.dto;


import com.crm.partner.entities.PartnerContract;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyDto {
    private String name;

    private String taxId;

    private String address;

    private String contactEmail;

    private String contactPhone;
}
