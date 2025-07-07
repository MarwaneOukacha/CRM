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
        Designer designer = designerDao.save(dto);
        return designerMapper.toResponseDTO(designer);
    }

    @Override
    public DesignerResponseDTO getById(String id) {
        Designer designer = designerDao.findById(UUID.fromString(id));
        return designerMapper.toResponseDTO(designer);
    }

    @Override
    public Page<DesignerResponseDTO> search(SearchDesignerCriteria criteria, Pageable pageable) {
        return designerDao.findAllWithCriteria(criteria, pageable)
                .map(designerMapper::toResponseDTO);
    }

    @Override
    public void delete(String id) {
        designerDao.delete(UUID.fromString(id));
    }

    @Override
    public DesignerUpdateResponseDTO update(String id, DesignerUpdateRequestDTO dto) {
        Designer updated = designerDao.updateDesigner(id, dto);
        return designerMapper.toUpdateResponseDTO(updated);
    }
}
