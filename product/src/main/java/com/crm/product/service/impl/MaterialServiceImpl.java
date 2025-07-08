package com.crm.product.service.impl;

import com.crm.product.dao.MaterialDao;
import com.crm.product.entities.Material;
import com.crm.product.entities.dto.SearchMaterialCriteria;
import com.crm.product.entities.dto.request.MaterialRequestDTO;
import com.crm.product.entities.dto.request.MaterialUpdateRequestDTO;
import com.crm.product.entities.dto.response.MaterialResponseDTO;
import com.crm.product.entities.dto.response.MaterialUpdateResponseDTO;
import com.crm.product.mapper.MaterialMapper;
import com.crm.product.service.MaterialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaterialServiceImpl implements MaterialService {

    private final MaterialDao materialDao;
    private final MaterialMapper materialMapper;

    @Override
    public MaterialResponseDTO create(MaterialRequestDTO material) {
        log.info("MaterialServiceImpl::create - Creating material: {}", material);
        Material entity = materialMapper.toResponse(material);
        Material saved = materialDao.save(entity);
        MaterialResponseDTO response = materialMapper.toMaterialResponseDTO(saved);
        log.info("MaterialServiceImpl::create - Created material with id: {}", response.getId());
        return response;
    }

    @Override
    public MaterialResponseDTO findById(String id) {
        log.info("MaterialServiceImpl::findById - Fetching material with id: {}", id);
        Material found = materialDao.findById(UUID.fromString(id));
        MaterialResponseDTO response = materialMapper.toMaterialResponseDTO(found);
        log.info("MaterialServiceImpl::findById - Found material: {}", response);
        return response;
    }

    @Override
    public Page<MaterialResponseDTO> search(SearchMaterialCriteria criteria, Pageable pageable) {
        log.info("MaterialServiceImpl::search - Searching materials with criteria: {}", criteria);
        Page<MaterialResponseDTO> results = materialDao.findAllWithCriteria(criteria, pageable)
                .map(materialMapper::toResponseDto);
        log.info("MaterialServiceImpl::search - Found {} materials", results.getTotalElements());
        return results;
    }

    @Override
    public void delete(String id) {
        log.info("MaterialServiceImpl::delete - Deleting material with id: {}", id);
        materialDao.delete(UUID.fromString(id));
        log.info("MaterialServiceImpl::delete - Deleted material with id: {}", id);
    }

    @Override
    public MaterialUpdateResponseDTO update(String id, MaterialUpdateRequestDTO dto) {
        log.info("MaterialServiceImpl::update - Updating material with id: {}, data: {}", id, dto);
        Material updated = materialDao.updateMaterial(id, dto);
        MaterialUpdateResponseDTO response = materialMapper.toUpdateResponseDto(updated);
        log.info("MaterialServiceImpl::update - Updated material with id: {}", id);
        return response;
    }
}
