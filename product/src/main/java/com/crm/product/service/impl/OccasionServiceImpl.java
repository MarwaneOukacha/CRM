package com.crm.product.service.impl;

import com.crm.product.dao.OccasionDao;
import com.crm.product.entities.Occasion;
import com.crm.product.entities.dto.request.OccasionRequestDTO;
import com.crm.product.entities.dto.request.OccasionUpdateRequestDTO;
import com.crm.product.entities.dto.response.OccasionResponseDTO;
import com.crm.product.mapper.OccasionMapper;
import com.crm.product.service.OccasionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OccasionServiceImpl implements OccasionService {

    private final OccasionDao occasionDao;
    private final OccasionMapper occasionMapper;

    @Override
    public OccasionResponseDTO create(OccasionRequestDTO dto) {
        Occasion occasion = occasionMapper.toEntity(dto);
        return occasionMapper.toResponseDTO(occasionDao.save(occasion));
    }

    @Override
    public OccasionResponseDTO getById(UUID id) {
        return occasionMapper.toResponseDTO(occasionDao.findById(id));
    }

    @Override
    public List<OccasionResponseDTO> getAll() {
        return occasionDao.findAll().stream()
                .map(occasionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        occasionDao.delete(id);
    }

    @Override
    public OccasionResponseDTO update(UUID id, OccasionUpdateRequestDTO dto) {
        Occasion updated = occasionDao.update(id, dto);
        return occasionMapper.toResponseDTO(updated);
    }
}
