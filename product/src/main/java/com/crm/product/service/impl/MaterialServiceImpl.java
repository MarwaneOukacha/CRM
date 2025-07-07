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
        log.info("Creating material: {}", material);
        Material response = materialMapper.toResponse(material);
        return materialMapper.toMaterialResponseDTO(materialDao.save(response));
    }

    @Override
    public MaterialResponseDTO findById(String id) {
        Material byId = materialDao.findById(UUID.fromString(id));
        return materialMapper.toMaterialResponseDTO(byId);
    }



    @Override
    public Page<MaterialResponseDTO> search(SearchMaterialCriteria criteria, Pageable pageable) {
        return materialDao.findAllWithCriteria(criteria, pageable)
                .map(materialMapper::toResponseDto);
    }

    @Override
    public void delete(String id) {
        materialDao.delete(UUID.fromString(id));
    }

    @Override
    public MaterialUpdateResponseDTO update(String id, MaterialUpdateRequestDTO dto) {
        Material updated = materialDao.updateMaterial(id, dto);
        return materialMapper.toUpdateResponseDto(updated);
    }
}