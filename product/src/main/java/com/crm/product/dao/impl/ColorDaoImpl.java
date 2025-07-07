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

        return colorRepository.save(color);
    }

    @Override
    public Color findById(UUID id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Color not found"));
    }

    @Override
    public Page<Color> findAllWithCriteria(SearchColorCriteria criteria, Pageable pageable) {
        Specification<Color> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getName() != null && !criteria.getName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + criteria.getName().toLowerCase() + "%"));
            }

            if (criteria.getStatus() != null && !criteria.getStatus().isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("status")), criteria.getStatus().toLowerCase()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return colorRepository.findAll(specification, pageable);
    }

    @Override
    public void delete(UUID id) {
        colorRepository.deleteById(id);
    }

    @Override
    public Color updateColor(String id, ColorUpdateRequestDTO updatedDto) {
        Color color = findById(UUID.fromString(id));

        if (updatedDto.getName() != null && !updatedDto.getName().isEmpty()) {
            color.setName(updatedDto.getName());
        }

        if (updatedDto.getStatus() != null && !updatedDto.getStatus().isEmpty()) {
            color.setStatus(updatedDto.getStatus());
        }
        if (updatedDto.getColor() != null && !updatedDto.getColor().isEmpty()) {
            color.setColor(updatedDto.getColor());
        }

        return colorRepository.save(color);
    }
}
