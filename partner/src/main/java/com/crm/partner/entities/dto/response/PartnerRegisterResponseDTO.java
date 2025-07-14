package com.crm.partner.entities.dto.response;

import com.crm.partner.entities.PartnerContract;
import com.crm.partner.entities.dto.CompanyDto;
import com.crm.partner.entities.dto.ContractDto;
import com.crm.partner.enums.PartnerStatus;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@ToString
public class PartnerRegisterResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private PartnerStatus status;

    // Company Information
    private CompanyDto company;
    private List<ContractDto> contracts = new ArrayList<>();

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