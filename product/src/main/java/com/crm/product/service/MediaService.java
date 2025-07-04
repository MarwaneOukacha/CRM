package com.crm.product.service;

import com.crm.product.entities.dto.request.MediaRequestDTO;
import com.crm.product.entities.dto.response.MediaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MediaService {

    void addMedia(MediaRequestDTO mediaDTO);

    MediaResponseDTO getById(String id);

    Page<MediaResponseDTO> findAll(Pageable pageable);

    void remove(String id);
    MediaResponseDTO updateMedia(String id, MediaRequestDTO updatedDto);

    Page<MediaResponseDTO> getByProductId(String productId, Pageable pageable);
}

