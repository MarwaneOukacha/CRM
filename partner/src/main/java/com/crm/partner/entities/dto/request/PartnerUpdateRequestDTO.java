package com.crm.partner.entities.dto.request;

import com.crm.partner.entities.PartnerContract;
import com.crm.partner.entities.dto.CompanyDto;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class PartnerUpdateRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String address;
    private CompanyDto company;
    private List<PartnerContract> contracts = new ArrayList<>();

}