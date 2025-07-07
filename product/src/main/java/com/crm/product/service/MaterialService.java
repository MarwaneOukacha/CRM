package com.crm.product.service;

import com.crm.product.entities.Material;
import com.crm.product.entities.dto.SearchMaterialCriteria;
import com.crm.product.entities.dto.request.MaterialRequestDTO;
import com.crm.product.entities.dto.request.MaterialUpdateRequestDTO;
import com.crm.product.entities.dto.response.MaterialResponseDTO;
import com.crm.product.entities.dto.response.MaterialUpdateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MaterialService {
    MaterialResponseDTO create(MaterialRequestDTO material);
    MaterialResponseDTO findById(String id);
    Page<MaterialResponseDTO> search(SearchMaterialCriteria criteria, Pageable pageable);
    void delete(String id);
    MaterialUpdateResponseDTO update(String id, MaterialUpdateRequestDTO dto);
}
