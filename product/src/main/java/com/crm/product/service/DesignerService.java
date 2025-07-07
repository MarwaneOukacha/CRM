package com.crm.product.service;

import com.crm.product.entities.dto.SearchDesignerCriteria;
import com.crm.product.entities.dto.request.DesignerRequestDTO;
import com.crm.product.entities.dto.request.DesignerUpdateRequestDTO;
import com.crm.product.entities.dto.response.DesignerResponseDTO;
import com.crm.product.entities.dto.response.DesignerUpdateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DesignerService {
    DesignerResponseDTO create(DesignerRequestDTO dto);
    DesignerResponseDTO getById(String id);
    Page<DesignerResponseDTO> search(SearchDesignerCriteria criteria, Pageable pageable);
    void delete(String id);
    DesignerUpdateResponseDTO update(String id, DesignerUpdateRequestDTO dto);
}

