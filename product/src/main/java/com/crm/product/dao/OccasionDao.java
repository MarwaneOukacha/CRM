package com.crm.product.dao;

import com.crm.product.entities.Occasion;
import com.crm.product.entities.dto.SearchOccasionCriteria;
import com.crm.product.entities.dto.request.OccasionUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface OccasionDao {
    Occasion save(Occasion occasion);
    Occasion findById(UUID id);
    List<Occasion> findAll();
    void delete(UUID id);
    Occasion update(UUID id, OccasionUpdateRequestDTO dto);

    Page<Occasion> findAllWithCriteria(SearchOccasionCriteria criteria, Pageable pageable);

}
