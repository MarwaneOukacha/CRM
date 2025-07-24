package com.crm.product.dao.impl;

import com.crm.product.dao.DesignerDao;
import com.crm.product.entities.Designer;
import com.crm.product.entities.dto.SearchDesignerCriteria;
import com.crm.product.entities.dto.request.DesignerRequestDTO;
import com.crm.product.entities.dto.request.DesignerUpdateRequestDTO;
import com.crm.product.repository.DesignerRepository;
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
public class DesignerDaoImpl implements DesignerDao {

    private final DesignerRepository designerRepository;

    @Override
    public Designer save(DesignerRequestDTO dto) {
        log.info("DesignerDaoImpl::save - Saving new designer: {}", dto);
        Designer designer = new Designer();
        designer.setName(dto.getName());
        designer.setStatus(dto.getStatus());
        Designer saved = designerRepository.save(designer);
        log.info("DesignerDaoImpl::save - Saved designer with id: {}", saved.getId());
        return saved;
    }

    @Override
    public Designer findById(UUID id) {
        log.info("DesignerDaoImpl::findById - Finding designer by id: {}", id);
        Designer found = designerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Designer not found with id: " + id));
        log.info("DesignerDaoImpl::findById - Found designer: {}", found);
        return found;
    }

    @Override
    public Page<Designer> findAllWithCriteria(SearchDesignerCriteria criteria, Pageable pageable) {
        log.info("DesignerDaoImpl::findAllWithCriteria - Searching designers with criteria: {}", criteria);
        Specification<Designer> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
                String keyword=criteria.getKeyword();
                Predicate namePredicate = cb.like(cb.lower(root.get("name")), keyword);
                Predicate statusPredicate = cb.equal(cb.lower(root.get("status")), keyword);

                predicates.add(cb.or(namePredicate, statusPredicate));
            }



            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Designer> result = designerRepository.findAll(specification, pageable);
        log.info("DesignerDaoImpl::findAllWithCriteria - Found {} designers", result.getTotalElements());
        return result;
    }

    @Override
    public void delete(UUID id) {
        log.info("DesignerDaoImpl::delete - Deleting designer with id: {}", id);
        designerRepository.deleteById(id);
        log.info("DesignerDaoImpl::delete - Deleted designer with id: {}", id);
    }

    @Override
    public Designer updateDesigner(String id, DesignerUpdateRequestDTO updatedDto) {
        log.info("DesignerDaoImpl::updateDesigner - Updating designer with id: {}, data: {}", id, updatedDto);
        Designer designer = findById(UUID.fromString(id));

        if (updatedDto.getName() != null && !updatedDto.getName().isEmpty()) {
            designer.setName(updatedDto.getName());
        }

        if (updatedDto.getStatus() != null) {
            designer.setStatus(updatedDto.getStatus());
        }

        Designer updated = designerRepository.save(designer);
        log.info("DesignerDaoImpl::updateDesigner - Updated designer with id: {}", updated.getId());
        return updated;
    }
}
