package com.crm.product.service.impl;


import com.crm.product.dao.CaratDao;
import com.crm.product.entities.Carat;
import com.crm.product.entities.dto.SearchCaratCriteria;
import com.crm.product.entities.dto.request.CaratRequestDTO;
import com.crm.product.entities.dto.request.CaratUpdateRequestDTO;
import com.crm.product.entities.dto.response.CaratResponseDTO;
import com.crm.product.entities.dto.response.CaratUpdateResponseDTO;
import com.crm.product.mapper.CaratMapper;
import com.crm.product.service.CaratService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaratServiceImpl implements CaratService {

    private final CaratDao caratDao;
    private final CaratMapper caratMapper;

    @Override
    public CaratResponseDTO create(CaratRequestDTO dto) {
        Carat carat = new Carat();
        carat.setName(dto.getName());
        carat.setStatus(dto.getStatus());
        return caratMapper.toResponseDTO(caratDao.save(carat));
    }

    @Override
    public CaratResponseDTO getById(String id) {
        return caratMapper.toResponseDTO(caratDao.findById(UUID.fromString(id)));
    }

    @Override
    public Page<CaratResponseDTO> search(SearchCaratCriteria criteria, Pageable pageable) {
        return caratDao.findAllWithCriteria(criteria, pageable)
                .map(caratMapper::toResponseDTO);
    }

    @Override
    public void delete(String id) {
        caratDao.delete(UUID.fromString(id));
    }

    @Override
    public CaratUpdateResponseDTO update(String id, CaratUpdateRequestDTO dto) {
        Carat updated = caratDao.updateCarat(id, dto);
        return caratMapper.toUpdateResponseDTO(updated);
    }
}
