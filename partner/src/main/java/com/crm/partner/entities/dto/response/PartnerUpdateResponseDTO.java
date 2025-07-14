package com.crm.partner.entities.dto.response;

import com.crm.partner.entities.PartnerContract;
import com.crm.partner.entities.dto.CompanyDto;
import com.crm.partner.entities.dto.ContractDto;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
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
    private CompanyDto company;
    private List<ContractDto> contracts = new ArrayList<>();
}