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
    // Identity Information
    private String passportSeries;
    private String passportNumber;
    private String finCode;

    // Bank Details
    private String receivingBankName;
    private String receivingBankCurrency;
    private String bankTIN;
    private String bankSwiftCode;
    private String bankAccountNumber;

}