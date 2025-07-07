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
        return materialRepository.save(material);
    }

    @Override
    public Material findById(UUID id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material not found"));
    }

    @Override
    public Page<Material> findAllWithCriteria(SearchMaterialCriteria criteria, Pageable pageable) {
        log.info("Searching materials with criteria: {}", criteria);

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

        return materialRepository.findAll(specification, pageable);
    }

    @Override
    public void delete(UUID id) {
        materialRepository.deleteById(id);
    }

    @Override
    public Material updateMaterial(String id, MaterialUpdateRequestDTO updatedDto) {
        Material material = findById(UUID.fromString(id));

        if (updatedDto.getName() != null && !updatedDto.getName().isEmpty()) {
            material.setName(updatedDto.getName());
        }

        if (updatedDto.getStatus() != null && !updatedDto.getStatus().isEmpty()) {
            material.setStatus(updatedDto.getStatus());
        }

        return materialRepository.save(material);
    }
}
