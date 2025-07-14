package com.crm.partner.entities.dto.response;
import com.crm.partner.entities.PartnerContract;
import com.crm.partner.entities.dto.CompanyDto;
import com.crm.partner.entities.dto.ContractDto;
import com.crm.partner.enums.PartnerStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
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
    private CompanyDto company;
    private List<ContractDto> contracts = new ArrayList<>();

}