package com.crm.partner.entities.dto.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PartnerRegisterRequestDTO {

    // Partner Information
    private String name;
    private String email;
    private String phone;
    private String address;

    // Identity Information
    private String passportSeries;
    private String passportNumber;
    private String finCode;

    // Company Information
    private String companyName;

    private String notes;

    // Bank Details
    private String receivingBankName;
    private String receivingBankCurrency;
    private String bankTIN;
    private String bankSwiftCode;
    private String bankAccountNumber;
}

