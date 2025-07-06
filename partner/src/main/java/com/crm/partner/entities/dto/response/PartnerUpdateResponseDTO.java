package com.crm.partner.entities.dto.response;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;
import java.time.LocalDateTime;
@Data
@ToString
public class PartnerUpdateResponseDTO {
    private UUID partnerId;
    private String status;

}