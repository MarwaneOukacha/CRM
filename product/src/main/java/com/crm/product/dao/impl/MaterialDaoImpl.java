package com.crm.product.dao.impl;

import com.crm.product.dao.MaterialDao;
import com.crm.product.entities.Material;
import com.crm.product.entities.Product;
import com.crm.product.entities.dto.SearchMaterialCriteria;
import com.crm.product.entities.dto.request.MaterialUpdateRequestDTO;
import com.crm.product.repository.MaterialRepository;
import com.crm.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MaterialDaoImpl implements MaterialDao {

    private final MaterialRepository materialRepository;
    private final ProductRepository productRepository;

    @Override
    public Material save(Material material) {
        log.info("MaterialDaoImpl::save - Saving material: {}", material);
        Material saved = materialRepository.save(material);
        log.info("MaterialDaoImpl::save - Saved material with id: {}", saved.getId());
        return saved;
    }

    @Override
    public Material findById(UUID id) {
        log.info("MaterialDaoImpl::findById - Finding material by id: {}", id);
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found with id: " + id));
        log.info("MaterialDaoImpl::findById - Found material: {}", material);
        return material;
    }

    @Override
    public Page<Material> findAllWithCriteria(SearchMaterialCriteria criteria, Pageable pageable) {
        log.info("MaterialDaoImpl::findAllWithCriteria - Searching materials with criteria: {}", criteria);

        Specification<Material> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getName() != null && !criteria.getName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
            }

            if (criteria.getStatus() != null && !criteria.getStatus().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("status")), criteria.getStatus().toLowerCase()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Material> results = materialRepository.findAll(specification, pageable);
        log.info("MaterialDaoImpl::findAllWithCriteria - Found {} materials", results.getTotalElements());
        return results;
    }

    @Override
    public void delete(UUID id) {
        log.info("MaterialDaoImpl::delete - Deleting material with id: {}", id);
        materialRepository.deleteById(id);
        log.info("MaterialDaoImpl::delete - Deleted material with id: {}", id);
    }

    @Override
    public Material updateMaterial(String id, MaterialUpdateRequestDTO updatedDto) {
        log.info("MaterialDaoImpl::updateMaterial - Updating material with id: {}, data: {}", id, updatedDto);
        Material material = findById(UUID.fromString(id));

        if (updatedDto.getName() != null && !updatedDto.getName().isEmpty()) {
            material.setName(updatedDto.getName());
        }

        if (updatedDto.getStatus() != null) {
            material.setStatus(updatedDto.getStatus());
        }

        Material updated = materialRepository.save(material);
        log.info("MaterialDaoImpl::updateMaterial - Updated material with id: {}", updated.getId());
        return updated;
    }
}
