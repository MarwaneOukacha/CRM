package com.crm.product.service.impl;

import com.crm.product.dao.MediaDao;
import com.crm.product.entities.Media;
import com.crm.product.entities.dto.request.MediaRequestDTO;
import com.crm.product.entities.dto.response.MediaResponseDTO;
import com.crm.product.mapper.MediaMapper;
import com.crm.product.service.MediaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    private final MediaDao mediaDao;
    private final MediaMapper mediaMapper;

    @Override
    public void addMedia(MediaRequestDTO mediaDTO) {
        log.info("Adding media: {}", mediaDTO);
        Media media = mediaMapper.toEntity(mediaDTO);
        mediaDao.save(media);
        log.debug("Media saved with ID: {}", media.getId());
    }

    @Override
    public MediaResponseDTO getById(String id) {
        log.info("Getting media by ID: {}", id);
        Media media = mediaDao.findById(UUID.fromString(id));
        if (media == null) {
            log.warn("No media found for ID: {}", id);
            throw new EntityNotFoundException("Media not found");
        }
        return mediaMapper.toResponseDTO(media);
    }

    @Override
    public Page<MediaResponseDTO> findAll(Pageable pageable) {
        log.info("Fetching all media (pageable: {})", pageable);
        return mediaDao.findAll(pageable)
                .map(mediaMapper::toResponseDTO);
    }

    @Override
    public void remove(String id) {
        log.info("Deleting media with ID: {}", id);
        mediaDao.delete(UUID.fromString(id));
        log.debug("Media deleted with ID: {}", id);
    }

    @Override
    public Page<MediaResponseDTO> getByProductId(String productId, Pageable pageable) {
        log.info("Getting media by product ID: {}", productId);
        return mediaDao.findByProductId(UUID.fromString(productId), pageable)
                .map(mediaMapper::toResponseDTO);
    }
    @Override
    public MediaResponseDTO updateMedia(String id, MediaRequestDTO updatedDto) {
        log.info("Updating media with ID: {}", id);
        Media existingMedia = mediaDao.findById(UUID.fromString(id));
        if (existingMedia == null) {
            log.warn("Cannot update: Media not found with ID: {}", id);
            throw new EntityNotFoundException("Media not found");
        }

        mediaMapper.updateEntityFromDto(updatedDto, existingMedia);
        mediaDao.save(existingMedia);
        log.debug("Media updated: {}", existingMedia);

        return mediaMapper.toResponseDTO(existingMedia);
    }


}
