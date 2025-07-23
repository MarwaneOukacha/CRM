package com.crm.partner.entities.dto.response;
import com.crm.partner.enums.PartnerStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class PartnerProfileResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private PartnerStatus status;
    // Company Information
    private String companyName;
    private String code;

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