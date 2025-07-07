package com.crm.product.service;

import com.crm.product.entities.dto.SearchCaratCriteria;
import com.crm.product.entities.dto.request.CaratRequestDTO;
import com.crm.product.entities.dto.request.CaratUpdateRequestDTO;
import com.crm.product.entities.dto.response.CaratResponseDTO;
import com.crm.product.entities.dto.response.CaratUpdateResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CaratService {
    CaratResponseDTO create(CaratRequestDTO dto);
    CaratResponseDTO getById(String id);
    Page<CaratResponseDTO> search(SearchCaratCriteria criteria, Pageable pageable);
    void delete(String id);
    CaratUpdateResponseDTO update(String id, CaratUpdateRequestDTO dto);
}
