package com.crm.product.service;

import com.crm.product.entities.dto.SearchDailySalesCriteria;
import com.crm.product.entities.dto.request.DailySalesRequestDTO;
import com.crm.product.entities.dto.request.DailySalesUpdateRequestDTO;
import com.crm.product.entities.dto.response.DailySalesResponseDTO;
import com.crm.product.entities.dto.response.DailySalesUpdateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DailySalesService {
    DailySalesResponseDTO create(DailySalesRequestDTO dto);
    DailySalesResponseDTO getById(String id);
    Page<DailySalesResponseDTO> search(SearchDailySalesCriteria criteria, Pageable pageable);
    void delete(String id);
    DailySalesUpdateResponseDTO update(String id, DailySalesUpdateRequestDTO dto);
}
