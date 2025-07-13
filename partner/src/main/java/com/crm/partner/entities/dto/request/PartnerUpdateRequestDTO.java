package com.crm.partner.entities.dto.request;

import com.crm.partner.entities.dto.CompanyDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PartnerUpdateRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String address;
    private CompanyDto company;

}