package com.crm.partner.dao.impl;

import com.crm.partner.dao.PartnerDao;
import com.crm.partner.entities.Partner;
import com.crm.partner.entities.dto.SearchPartnerCriteria;
import com.crm.partner.repository.PartnerRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@RequiredArgsConstructor
public class PartnerDaoImpl implements PartnerDao {

    private static final Logger log = LoggerFactory.getLogger(PartnerDaoImpl.class);

    private final PartnerRepository partnerRepository;

    @Override
    public Partner save(Partner partner) {
        return partnerRepository.save(partner);
    }

    @Override
    public Optional<Partner> findById(String id) {
        return partnerRepository.findById(UUID.fromString(id));
    }

    @Override
    public Optional<Partner> findByContactEmail(String email) {
        return partnerRepository.findByContactEmail(email);
    }

    @Override
    public boolean existsByContactEmail(String email) {
        return partnerRepository.existsByContactEmail(email);
    }

    @Override
    public Page<Partner> findAllWithCriteria(SearchPartnerCriteria criteria, Pageable pageable) {
        log.info("Searching partners with criteria: {}", criteria);

        Specification<Partner> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getCompanyName() != null && !criteria.getCompanyName().isEmpty()) {
                String companyName = "%" + criteria.getCompanyName().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("companyName")), companyName));
            }

            if (criteria.getContactEmail() != null && !criteria.getContactEmail().isEmpty()) {
                String contactEmail = "%" + criteria.getContactEmail().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("contactEmail")), contactEmail));
            }

            if (criteria.getStatus() != null && !criteria.getStatus().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("status")), criteria.getStatus().toLowerCase()));
            }

            if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
                String keyword = "%" + criteria.getKeyword().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("contactName")), keyword)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Partner> result = partnerRepository.findAll(specification, pageable);
        log.debug("Found {} partners matching criteria", result.getTotalElements());
        return result;
    }

    @Override
    public void deleteById(String id) {
        partnerRepository.deleteById(UUID.fromString(id));
    }
}
