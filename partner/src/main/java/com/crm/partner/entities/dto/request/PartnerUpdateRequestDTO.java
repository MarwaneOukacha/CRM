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

}