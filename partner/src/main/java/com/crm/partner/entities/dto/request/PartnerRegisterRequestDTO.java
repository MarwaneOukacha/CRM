package com.crm.partner.entities.dto.request;

import com.crm.partner.entities.PartnerContract;
import com.crm.partner.entities.dto.CompanyDto;
import com.crm.partner.entities.dto.ContractDto;
import com.crm.partner.enums.ReturnFeePayer;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class PartnerRegisterRequestDTO {
    // Partner Information
    private String name;
    private String email;
    private String phone;
    private String address;

    // Company Information
    private CompanyDto company;
    private List<PartnerContract> contracts = new ArrayList<>();
    private String notes;
}
