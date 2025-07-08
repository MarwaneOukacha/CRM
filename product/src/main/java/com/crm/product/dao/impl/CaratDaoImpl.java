package com.crm.product.dao.impl;

import com.crm.product.dao.CaratDao;
import com.crm.product.entities.Carat;
import com.crm.product.entities.dto.SearchCaratCriteria;
import com.crm.product.entities.dto.request.CaratUpdateRequestDTO;
import com.crm.product.repository.CaratRepository;
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
public class CaratDaoImpl implements CaratDao {

    private final CaratRepository caratRepository;

    @Override
    public Carat save(Carat carat) {
        log.info("CaratDaoImpl::save - Saving carat: {}", carat);
        return caratRepository.save(carat);
    }

    @Override
    public Carat findById(UUID id) {
        log.info("CaratDaoImpl::findById - Looking for carat with ID: {}", id);
        return caratRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carat not found with id: " + id));
    }

    @Override
    public Page<Carat> findAllWithCriteria(SearchCaratCriteria criteria, Pageable pageable) {
        log.info("CaratDaoImpl::findAllWithCriteria - Searching carats with criteria: {}", criteria);

        Specification<Carat> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getName() != null && !criteria.getName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
            }

            if (criteria.getStatus() != null && !criteria.getStatus().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("status")), criteria.getStatus().toLowerCase()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Carat> result = caratRepository.findAll(specification, pageable);
        log.info("CaratDaoImpl::findAllWithCriteria - Found {} carats", result.getTotalElements());
        return result;
    }

    @Override
    public void delete(UUID id) {
        log.info("CaratDaoImpl::delete - Deleting carat with ID: {}", id);
        caratRepository.deleteById(id);
        log.info("CaratDaoImpl::delete - Successfully deleted carat with ID: {}", id);
    }

    @Override
    public Carat updateCarat(String id, CaratUpdateRequestDTO updatedDto) {
        log.info("CaratDaoImpl::updateCarat - Updating carat with ID: {}, Data: {}", id, updatedDto);
        Carat carat = findById(UUID.fromString(id));

        if (updatedDto.getName() != null && !updatedDto.getName().isEmpty()) {
            carat.setName(updatedDto.getName());
        }

        if (updatedDto.getStatus() != null ) {
            carat.setStatus(updatedDto.getStatus());
        }

        Carat updated = caratRepository.save(carat);
        log.info("CaratDaoImpl::updateCarat - Successfully updated carat with ID: {}", updated.getId());
        return updated;
    }
}
