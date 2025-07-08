package com.crm.product.service;

import com.crm.product.entities.dto.request.OccasionRequestDTO;
import com.crm.product.entities.dto.request.OccasionUpdateRequestDTO;
import com.crm.product.entities.dto.response.OccasionResponseDTO;

import java.util.List;
import java.util.UUID;

public interface OccasionService {
    OccasionResponseDTO create(OccasionRequestDTO dto);
    OccasionResponseDTO getById(UUID id);
    List<OccasionResponseDTO> getAll();
    void delete(UUID id);
    OccasionResponseDTO update(UUID id, OccasionUpdateRequestDTO dto);
}

