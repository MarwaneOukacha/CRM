package com.crm.product.dao;

import com.crm.product.entities.Designer;
import com.crm.product.entities.dto.SearchDesignerCriteria;
import com.crm.product.entities.dto.request.DesignerUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DesignerDao {
    Designer save(Designer designer);
    Designer findById(UUID id);
    Page<Designer> findAllWithCriteria(SearchDesignerCriteria criteria, Pageable pageable);
    void delete(UUID id);
    Designer updateDesigner(String id, DesignerUpdateRequestDTO updatedDto);
}
