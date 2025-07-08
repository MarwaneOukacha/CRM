package com.crm.product.service.impl;

import com.crm.product.dao.ColorDao;
import com.crm.product.entities.Color;
import com.crm.product.entities.dto.SearchColorCriteria;
import com.crm.product.entities.dto.request.ColorRequestDTO;
import com.crm.product.entities.dto.request.ColorUpdateRequestDTO;
import com.crm.product.entities.dto.response.ColorResponseDTO;
import com.crm.product.entities.dto.response.ColorUpdateResponseDTO;
import com.crm.product.mapper.ColorMapper;
import com.crm.product.service.ColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ColorServiceImpl implements ColorService {

    private final ColorDao colorDao;
    private final ColorMapper colorMapper;

    @Override
    public ColorResponseDTO create(ColorRequestDTO color) {
        log.info("ColorServiceImpl::create - Creating color: {}", color);
        Color color1 = colorMapper.toColor(color);
        return colorMapper.toResponseDTO(colorDao.save(color1));
    }

    @Override
    public ColorResponseDTO getById(String id) {
        log.info("ColorServiceImpl::getById - Fetching color with id: {}", id);
        return colorMapper.toResponseDTO(colorDao.findById(UUID.fromString(id)));
    }

    @Override
    public Page<ColorResponseDTO> search(SearchColorCriteria criteria, Pageable pageable) {
        log.info("ColorServiceImpl::search - Searching colors with criteria: {}", criteria);
        return colorDao.findAllWithCriteria(criteria, pageable)
                .map(colorMapper::toResponseDTO);
    }

    @Override
    public void delete(String id) {
        log.info("ColorServiceImpl::delete - Deleting color with id: {}", id);
        colorDao.delete(UUID.fromString(id));
        log.info("ColorServiceImpl::delete - Successfully deleted color with id: {}", id);
    }

    @Override
    public ColorUpdateResponseDTO update(String id, ColorUpdateRequestDTO dto) {
        log.info("ColorServiceImpl::update - Updating color with id: {}, data: {}", id, dto);
        return colorMapper.toUpdateResponseDTO(colorDao.updateColor(id, dto));
    }
}
