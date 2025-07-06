package com.crm.partner.entities.dto.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PartnerRegisterResponseDTO {
    private String partnerId;
    private String status;

}