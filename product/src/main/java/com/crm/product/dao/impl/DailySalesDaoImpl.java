package com.crm.product.dao.impl;

import com.crm.product.dao.DailySalesDao;
import com.crm.product.entities.DailySales;
import com.crm.product.entities.dto.SearchDailySalesCriteria;
import com.crm.product.entities.dto.request.DailySalesUpdateRequestDTO;
import com.crm.product.repository.DailySalesRepository;
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
public class DailySalesDaoImpl implements DailySalesDao {

    private final DailySalesRepository dailySalesRepository;

    @Override
    public DailySales save(DailySales dailySales) {
        log.info("DailySalesDaoImpl::save - Saving DailySales: {}", dailySales);
        DailySales saved = dailySalesRepository.save(dailySales);
        log.info("DailySalesDaoImpl::save - Saved DailySales with id: {}", saved.getId());
        return saved;
    }

    @Override
    public DailySales findById(UUID id) {
        log.info("DailySalesDaoImpl::findById - Finding DailySales by id: {}", id);
        DailySales found = dailySalesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DailySales not found with id: " + id));
        log.info("DailySalesDaoImpl::findById - Found DailySales: {}", found);
        return found;
    }

    @Override
    public Page<DailySales> findAllWithCriteria(SearchDailySalesCriteria criteria, Pageable pageable) {
        log.info("DailySalesDaoImpl::findAllWithCriteria - Searching with criteria: {}", criteria);

        Specification<DailySales> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
                String kw = "%" + criteria.getKeyword().toLowerCase() + "%";
                Predicate namePredicate = cb.like(cb.lower(root.get("status")), kw);
                predicates.add(cb.or(namePredicate));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<DailySales> result = dailySalesRepository.findAll(specification, pageable);
        log.info("DailySalesDaoImpl::findAllWithCriteria - Found {} records", result.getTotalElements());
        return result;
    }

    @Override
    public void delete(UUID id) {
        log.info("DailySalesDaoImpl::delete - Deleting DailySales with id: {}", id);
        dailySalesRepository.deleteById(id);
        log.info("DailySalesDaoImpl::delete - Deleted DailySales with id: {}", id);
    }

    @Override
    public DailySales updateDailySales(String id, DailySalesUpdateRequestDTO updatedDto) {
        log.info("DailySalesDaoImpl::updateDailySales - Updating DailySales with id: {}, data: {}", id, updatedDto);
        DailySales entity = findById(UUID.fromString(id));

        if (updatedDto.getDays() != null) {
            entity.setDays(updatedDto.getDays());
        }

        if (updatedDto.getPercent() != null) {
            entity.setPercent(updatedDto.getPercent());
        }

        if (updatedDto.getStatus() != null) {
            entity.setStatus(updatedDto.getStatus());
        }

        DailySales updated = dailySalesRepository.save(entity);
        log.info("DailySalesDaoImpl::updateDailySales - Updated DailySales with id: {}", updated.getId());
        return updated;
    }
}
