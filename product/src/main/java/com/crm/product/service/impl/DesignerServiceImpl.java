package com.crm.product.service.impl;

import com.crm.product.dao.DesignerDao;
import com.crm.product.entities.Designer;
import com.crm.product.entities.dto.SearchDesignerCriteria;
import com.crm.product.entities.dto.request.DesignerRequestDTO;
import com.crm.product.entities.dto.request.DesignerUpdateRequestDTO;
import com.crm.product.entities.dto.response.DesignerResponseDTO;
import com.crm.product.entities.dto.response.DesignerUpdateResponseDTO;
import com.crm.product.mapper.DesignerMapper;
import com.crm.product.service.DesignerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DesignerServiceImpl implements DesignerService {

    private final DesignerDao designerDao;
    private final DesignerMapper designerMapper;

    @Override
    public DesignerResponseDTO create(DesignerRequestDTO dto) {
        log.info("DesignerServiceImpl::create - Creating designer: {}", dto);
        Designer designer = designerDao.save(dto);
        DesignerResponseDTO response = designerMapper.toResponseDTO(designer);
        log.info("DesignerServiceImpl::create - Created designer with id: {}", response.getId());
        return response;
    }

    @Override
    public DesignerResponseDTO getById(String id) {
        log.info("DesignerServiceImpl::getById - Fetching designer by id: {}", id);
        Designer designer = designerDao.findById(UUID.fromString(id));
        DesignerResponseDTO response = designerMapper.toResponseDTO(designer);
        log.info("DesignerServiceImpl::getById - Found designer: {}", response);
        return response;
    }

    @Override
    public Page<DesignerResponseDTO> search(SearchDesignerCriteria criteria, Pageable pageable) {
        log.info("DesignerServiceImpl::search - Searching designers with criteria: {}", criteria);
        Page<DesignerResponseDTO> results = designerDao.findAllWithCriteria(criteria, pageable)
                .map(designerMapper::toResponseDTO);
        log.info("DesignerServiceImpl::search - Found {} designers", results.getTotalElements());
        return results;
    }

    @Override
    public void delete(String id) {
        log.info("DesignerServiceImpl::delete - Deleting designer with id: {}", id);
        designerDao.delete(UUID.fromString(id));
        log.info("DesignerServiceImpl::delete - Deleted designer with id: {}", id);
    }

    @Override
    public DesignerUpdateResponseDTO update(String id, DesignerUpdateRequestDTO dto) {
        log.info("DesignerServiceImpl::update - Updating designer with id: {}, data: {}", id, dto);
        Designer updated = designerDao.updateDesigner(id, dto);
        DesignerUpdateResponseDTO response = designerMapper.toUpdateResponseDTO(updated);
        log.info("DesignerServiceImpl::update - Updated designer with id: {}", id);
        return response;
    }
}
