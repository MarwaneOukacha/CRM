package com.crm.partner.entities.dto.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PartnerUpdateRequestDTO {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String companyName;
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