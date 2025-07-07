package com.crm.product.dao;

import com.crm.product.entities.Color;
import com.crm.product.entities.dto.SearchColorCriteria;
import com.crm.product.entities.dto.request.ColorUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ColorDao {
    Color save(Color color);
    Color findById(UUID id);
    Page<Color> findAllWithCriteria(SearchColorCriteria criteria, Pageable pageable);
    void delete(UUID id);
    Color updateColor(String id, ColorUpdateRequestDTO updatedDto);
}