package com.crm.order.dao.impl;

import com.crm.order.dao.CampaignDiscountDao;
import com.crm.order.entities.CampaignDiscount;
import com.crm.order.entities.model.CampaignDiscountSearchCriteriaDTO;
import com.crm.order.repository.CampaignDiscountRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CampaignDiscountDaoImpl implements CampaignDiscountDao {
    private final CampaignDiscountRepository repository;

    @Override
    public CampaignDiscount save(CampaignDiscount campaignDiscount) {
        return repository.save(campaignDiscount);
    }

    @Override
    public CampaignDiscount update(CampaignDiscount campaignDiscount) {
        return repository.save(campaignDiscount);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<CampaignDiscount> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Page<CampaignDiscount> search(CampaignDiscountSearchCriteriaDTO criteria, Pageable pageable) {
        log.info("CampaignDiscountDaoImpl::findAllWithCriteria - Searching with criteria: {}", criteria);

        Specification<CampaignDiscount> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
                String keyword = "%" + criteria.getKeyword().toLowerCase() + "%";
                Predicate title = cb.like(cb.lower(root.get("title")), keyword);
                Predicate desc = cb.like(cb.lower(root.get("description")), keyword);

                predicates.add(cb.or(title, desc));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        Page<CampaignDiscount> result = repository.findAll(specification, pageable);
        log.info("CampaignDiscountDaoImpl::findAllWithCriteria - Found {} ", result.getTotalElements());
        return result;
    }
}
