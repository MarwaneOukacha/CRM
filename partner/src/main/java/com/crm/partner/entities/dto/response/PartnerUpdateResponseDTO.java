package com.crm.partner.entities.dto.response;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class PartnerUpdateResponseDTO {
    private UUID id;
    private String status;
    private String name;
    private String email;
    private String phone;
    private String address;

    // Company Information
    private String companyName;
    // Identity Information
    private String passportSeries;
    private String passportNumber;
    private String finCode;
    private String code;

    // Bank Details
    private String receivingBankName;
    private String receivingBankCurrency;
    private String bankTIN;
    private String bankSwiftCode;
    private String bankAccountNumber;
}