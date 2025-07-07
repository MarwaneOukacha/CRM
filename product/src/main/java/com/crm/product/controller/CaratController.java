package com.crm.product.controller;

import com.crm.product.entities.dto.SearchCaratCriteria;
import com.crm.product.entities.dto.request.CaratRequestDTO;
import com.crm.product.entities.dto.request.CaratUpdateRequestDTO;
import com.crm.product.entities.dto.response.CaratResponseDTO;
import com.crm.product.entities.dto.response.CaratUpdateResponseDTO;
import com.crm.product.service.CaratService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carat")
@RequiredArgsConstructor
@Slf4j
public class CaratController {

    private final CaratService caratService;

    @PostMapping
    public CaratResponseDTO create(@RequestBody CaratRequestDTO dto) {
        log.info("Creating carat: {}", dto);
        return caratService.create(dto);
    }

    @GetMapping("/{id}")
    public CaratResponseDTO getById(@PathVariable String id) {
        return caratService.getById(id);
    }

    @GetMapping
    public Page<CaratResponseDTO> search(SearchCaratCriteria criteria, Pageable pageable) {
        return caratService.search(criteria, pageable);
    }

    @PutMapping("/{id}")
    public CaratUpdateResponseDTO update(@PathVariable String id, @RequestBody CaratUpdateRequestDTO dto) {
        return caratService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        caratService.delete(id);
    }
}
