package com.crm.product.dao;

import com.crm.product.entities.Material;
import com.crm.product.entities.dto.SearchMaterialCriteria;
import com.crm.product.entities.dto.request.MaterialUpdateRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MaterialDao {
    Material save(Material material);
    Material findById(UUID id);
    Page<Material> findAllWithCriteria(SearchMaterialCriteria criteria, Pageable pageable);
    void delete(UUID id);
    Material updateMaterial(String id, MaterialUpdateRequestDTO updatedDto);
}
