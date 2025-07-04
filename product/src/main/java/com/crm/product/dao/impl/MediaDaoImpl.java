package com.crm.product.dao.impl;

import com.crm.product.dao.MediaDao;
import com.crm.product.entities.Media;
import com.crm.product.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaDaoImpl implements MediaDao {

    private final MediaRepository mediaRepository;

    @Override
    public void save(Media media) {
        log.info("Saving media: {}", media);
        mediaRepository.save(media);
        log.debug("Media saved successfully with ID: {}", media.getId());
    }

    @Override
    public Media findById(UUID id) {
        log.info("Finding media by ID: {}", id);
        return mediaRepository.findById(id)
                .map(media -> {
                    log.debug("Media found: {}", media);
                    return media;
                })
                .orElseGet(() -> {
                    log.warn("No media found with ID: {}", id);
                    return null;
                });
    }

    @Override
    public Page<Media> findAll(Pageable pageable) {
        log.info("Fetching all media with pagination: {}", pageable);
        Page<Media> result = mediaRepository.findAll(pageable);
        log.debug("Fetched {} media items", result.getTotalElements());
        return result;
    }

    @Override
    public void delete(UUID id) {
        log.info("Deleting media with ID: {}", id);
        mediaRepository.deleteById(id);
        log.debug("Media with ID {} deleted", id);
    }

    @Override
    public Page<Media> findByProductId(UUID productId, Pageable pageable) {
        log.info("Finding media by product ID: {}", productId);
        Page<Media> result = mediaRepository.findByProductId(productId, pageable);
        log.debug("Found {} media items for product ID {}", result.getTotalElements(), productId);
        return result;
    }
}
