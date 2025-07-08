package com.crm.product.entities.dto.response;

import com.crm.product.entities.dto.AbstractDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class CaratUpdateResponseDTO extends AbstractDTO {
    private UUID id;

}