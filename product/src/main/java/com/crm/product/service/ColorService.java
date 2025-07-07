package com.crm.product.service;

import com.crm.product.entities.Color;
import com.crm.product.entities.dto.SearchColorCriteria;
import com.crm.product.entities.dto.request.ColorRequestDTO;
import com.crm.product.entities.dto.request.ColorUpdateRequestDTO;
import com.crm.product.entities.dto.response.ColorResponseDTO;
import com.crm.product.entities.dto.response.ColorUpdateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ColorService {
    ColorResponseDTO create(ColorRequestDTO color);
    ColorResponseDTO getById(String id);
    Page<ColorResponseDTO> search(SearchColorCriteria criteria, Pageable pageable);
    void delete(String id);
    ColorUpdateResponseDTO update(String id, ColorUpdateRequestDTO dto);
}
