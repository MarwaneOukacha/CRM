package com.crm.product.dao;

import com.crm.product.entities.DailySales;
import com.crm.product.entities.dto.SearchDailySalesCriteria;
import com.crm.product.entities.dto.request.DailySalesUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.UUID;

public interface DailySalesDao {
    DailySales save(DailySales dailySales);
    DailySales findById(UUID id);
    Page<DailySales> findAllWithCriteria(SearchDailySalesCriteria criteria, Pageable pageable);
    void delete(UUID id);
    DailySales updateDailySales(String id, DailySalesUpdateRequestDTO updatedDto);
}
