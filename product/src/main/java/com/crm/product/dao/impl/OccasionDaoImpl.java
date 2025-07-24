package com.crm.product.dao.impl;

import com.crm.product.dao.OccasionDao;
import com.crm.product.entities.Category;
import com.crm.product.entities.Occasion;
import com.crm.product.entities.dto.SearchCategoryCriteria;
import com.crm.product.entities.dto.SearchOccasionCriteria;
import com.crm.product.entities.dto.request.OccasionUpdateRequestDTO;
import com.crm.product.repository.OccasionRepository;
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
public class OccasionDaoImpl implements OccasionDao {

    private final OccasionRepository occasionRepository;

    @Override
    public Occasion save(Occasion occasion) {
        log.info("Saving occasion: {}", occasion);
        return occasionRepository.save(occasion);
    }

    @Override
    public Occasion findById(UUID id) {
        log.info("Finding occasion by ID: {}", id);
        return occasionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Occasion not found with id: " + id));
    }

    @Override
    public List<Occasion> findAll() {
        log.info("Retrieving all occasions");
        return occasionRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        log.info("Deleting occasion with ID: {}", id);
        occasionRepository.deleteById(id);
    }

    @Override
    public Occasion update(UUID id, OccasionUpdateRequestDTO dto) {
        Occasion occasion = findById(id);
        if (dto.getName() != null) occasion.setName(dto.getName());
        if (dto.getStatus() != null) occasion.setStatus(dto.getStatus());
        return occasionRepository.save(occasion);
    }

    @Override
    public Page<Occasion> findAllWithCriteria(SearchOccasionCriteria criteria, Pageable pageable) {
        log.info("OccasionDaoImpl::findAllWithCriteria - Searching categories with criteria: {}", criteria);

        Specification<Occasion> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
                String keyword = "%" + criteria.getKeyword().toLowerCase() + "%";
                Predicate namePredicate = cb.like(cb.lower(root.get("name")), keyword);
                Predicate statusPredicate = cb.equal(cb.lower(root.get("status")), criteria.getKeyword().toLowerCase());

                predicates.add(cb.or(namePredicate, statusPredicate));
            }



            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Page<Occasion> result = occasionRepository.findAll(specification, pageable);
        log.info("CategoryDaoImpl::findAllWithCriteria - Found {} categories", result.getTotalElements());
        return result;
    }
}
