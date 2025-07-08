package com.crm.product.dao.impl;

import com.crm.product.dao.ColorDao;
import com.crm.product.entities.Color;
import com.crm.product.entities.dto.SearchColorCriteria;
import com.crm.product.entities.dto.request.ColorUpdateRequestDTO;
import com.crm.product.mapper.ColorMapper;
import com.crm.product.repository.ColorRepository;
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
public class ColorDaoImpl implements ColorDao {

    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    @Override
    public Color save(Color color) {
        log.info("ColorDaoImpl::save - Saving color: {}", color);
        return colorRepository.save(color);
    }

    @Override
    public Color findById(UUID id) {
        log.info("ColorDaoImpl::findById - Finding color by id: {}", id);
        return colorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Color not found"));
    }

    @Override
    public Page<Color> findAllWithCriteria(SearchColorCriteria criteria, Pageable pageable) {
        log.info("ColorDaoImpl::findAllWithCriteria - Searching colors with criteria: {}", criteria);

        Specification<Color> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
                String keyword=criteria.getKeyword();
                Predicate namePredicate = cb.like(cb.lower(root.get("name")), keyword);
                Predicate statusPredicate = cb.like(cb.lower(root.get("status")), keyword);

                predicates.add(cb.or(namePredicate, statusPredicate));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Color> result = colorRepository.findAll(specification, pageable);
        log.info("ColorDaoImpl::findAllWithCriteria - Found {} colors", result.getTotalElements());
        return result;
    }

    @Override
    public void delete(UUID id) {
        log.info("ColorDaoImpl::delete - Deleting color with id: {}", id);
        colorRepository.deleteById(id);
        log.info("ColorDaoImpl::delete - Successfully deleted color with id: {}", id);
    }

    @Override
    public Color updateColor(String id, ColorUpdateRequestDTO updatedDto) {
        log.info("ColorDaoImpl::updateColor - Updating color with id: {}, data: {}", id, updatedDto);
        Color color = findById(UUID.fromString(id));

        if (updatedDto.getName() != null && !updatedDto.getName().isEmpty()) {
            color.setName(updatedDto.getName());
        }

        if (updatedDto.getStatus() != null ) {
            color.setStatus(updatedDto.getStatus());
        }
        if (updatedDto.getColor() != null && !updatedDto.getColor().isEmpty()) {
            color.setColor(updatedDto.getColor());
        }

        Color updated = colorRepository.save(color);
        log.info("ColorDaoImpl::updateColor - Updated color with id: {}", updated.getId());
        return updated;
    }
}