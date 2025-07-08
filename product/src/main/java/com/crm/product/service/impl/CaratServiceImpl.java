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
        log.info("CaratServiceImpl::create - Creating carat with data: {}", dto);
        Carat carat = new Carat();
        carat.setName(dto.getName());
        carat.setStatus(dto.getStatus());
        Carat saved = caratDao.save(carat);
        log.info("CaratServiceImpl::create - Successfully created carat with ID: {}", saved.getId());
        return caratMapper.toResponseDTO(saved);
    }

    @Override
    public CaratResponseDTO getById(String id) {
        log.info("CaratServiceImpl.getById - Fetching carat with ID: {}", id);
        Carat carat = caratDao.findById(UUID.fromString(id));
        log.info("CaratServiceImpl::getById - Found carat: {}", carat);
        return caratMapper.toResponseDTO(carat);
    }

    @Override
    public Page<CaratResponseDTO> search(SearchCaratCriteria criteria, Pageable pageable) {
        log.info("CaratServiceImpl::search - Searching carats with criteria: {}", criteria);
        Page<CaratResponseDTO> result = caratDao.findAllWithCriteria(criteria, pageable)
                .map(caratMapper::toResponseDTO);
        log.info("CaratServiceImpl::search - Found {} carats", result.getTotalElements());
        return result;
    }

    @Override
    public void delete(String id) {
        log.info("CaratServiceImpl::delete - Deleting carat with ID: {}", id);
        caratDao.delete(UUID.fromString(id));
        log.info("CaratServiceImpl::delete - Successfully deleted carat with ID: {}", id);
    }

    @Override
    public CaratUpdateResponseDTO update(String id, CaratUpdateRequestDTO dto) {
        log.info("CaratServiceImpl::update - Updating carat with ID: {}, data: {}", id, dto);
        Carat updated = caratDao.updateCarat(id, dto);
        log.info("CaratServiceImpl::update - Successfully updated carat with ID: {}", id);
        return caratMapper.toUpdateResponseDTO(updated);
    }
}
