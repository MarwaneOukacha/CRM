package com.crm.product.entities.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PartnerDTO {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private boolean verified;

    private Double commissionRate;
    // other fields as needed
}
