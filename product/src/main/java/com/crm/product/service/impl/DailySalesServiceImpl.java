package com.crm.product.service.impl;

import com.crm.product.dao.DailySalesDao;
import com.crm.product.entities.DailySales;
import com.crm.product.entities.dto.SearchDailySalesCriteria;
import com.crm.product.entities.dto.request.DailySalesRequestDTO;
import com.crm.product.entities.dto.request.DailySalesUpdateRequestDTO;
import com.crm.product.entities.dto.response.DailySalesResponseDTO;
import com.crm.product.entities.dto.response.DailySalesUpdateResponseDTO;
import com.crm.product.mapper.DailySalesMapper;
import com.crm.product.service.DailySalesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailySalesServiceImpl implements DailySalesService {

    private final DailySalesDao dailySalesDao;
    private final DailySalesMapper dailySalesMapper;

    @Override
    public DailySalesResponseDTO create(DailySalesRequestDTO dto) {
        log.info("DailySalesServiceImpl::create - Creating DailySales with data: {}", dto);
        DailySales dailySales = new DailySales();
        dailySales.setDays(dto.getDays());
        dailySales.setPercent(dto.getPercent());
        dailySales.setStatus(dto.getStatus());
        DailySales saved = dailySalesDao.save(dailySales);
        log.info("DailySalesServiceImpl::create - Created DailySales with id: {}", saved.getId());
        return dailySalesMapper.toResponseDTO(saved);
    }

    @Override
    public DailySalesResponseDTO getById(String id) {
        log.info("DailySalesServiceImpl::getById - Fetching DailySales by id: {}", id);
        DailySales found = dailySalesDao.findById(UUID.fromString(id));
        return dailySalesMapper.toResponseDTO(found);
    }

    @Override
    public Page<DailySalesResponseDTO> search(SearchDailySalesCriteria criteria, Pageable pageable) {
        log.info("DailySalesServiceImpl::search - Searching DailySales with criteria: {}", criteria);
        Page<DailySales> results = dailySalesDao.findAllWithCriteria(criteria, pageable);
        log.info("DailySalesServiceImpl::search - Found {} DailySales records", results.getTotalElements());
        return results.map(dailySalesMapper::toResponseDTO);
    }

    @Override
    public void delete(String id) {
        log.info("DailySalesServiceImpl::delete - Deleting DailySales with id: {}", id);
        dailySalesDao.delete(UUID.fromString(id));
        log.info("DailySalesServiceImpl::delete - Deleted DailySales with id: {}", id);
    }

    @Override
    public DailySalesUpdateResponseDTO update(String id, DailySalesUpdateRequestDTO dto) {
        log.info("DailySalesServiceImpl::update - Updating DailySales with id: {}, data: {}", id, dto);
        DailySales updated = dailySalesDao.updateDailySales(id, dto);
        log.info("DailySalesServiceImpl::update - Updated DailySales with id: {}", updated.getId());
        return dailySalesMapper.toUpdateResponseDTO(updated);
    }
}
