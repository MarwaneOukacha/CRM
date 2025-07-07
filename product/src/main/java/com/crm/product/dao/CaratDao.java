package com.crm.product.dao;

import com.crm.product.entities.Carat;
import com.crm.product.entities.dto.SearchCaratCriteria;
import com.crm.product.entities.dto.request.CaratUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CaratDao {
    Carat save(Carat carat);
    Carat findById(UUID id);
    Page<Carat> findAllWithCriteria(SearchCaratCriteria criteria, Pageable pageable);
    void delete(UUID id);
    Carat updateCarat(String id, CaratUpdateRequestDTO updatedDto);
}
