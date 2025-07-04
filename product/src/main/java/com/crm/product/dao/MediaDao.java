package com.crm.product.dao;

import com.crm.product.entities.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MediaDao {
    void save(Media media);
    Media findById(UUID id);
    Page<Media> findAll(Pageable pageable);
    void delete(UUID id);
    Page<Media> findByProductId(UUID productId, Pageable pageable);
}
