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
        log.info("Received request to create Carat: {}", dto);
        CaratResponseDTO response = caratService.create(dto);
        log.info("Successfully created Carat with id: {}", response.getId());
        return response;
    }

    @GetMapping("/{id}")
    public CaratResponseDTO getById(@PathVariable String id) {
        log.info("Fetching Carat with id: {}", id);
        CaratResponseDTO response = caratService.getById(id);
        log.info("Found Carat: {}", response);
        return response;
    }

    @GetMapping
    public Page<CaratResponseDTO> search(SearchCaratCriteria criteria, Pageable pageable) {
        log.info("Searching Carats with criteria: {}", criteria);
        Page<CaratResponseDTO> results = caratService.search(criteria, pageable);
        log.info("Found {} Carats matching criteria", results.getTotalElements());
        return results;
    }

    @PutMapping("/{id}")
    public CaratUpdateResponseDTO update(@PathVariable String id, @RequestBody CaratUpdateRequestDTO dto) {
        log.info("Updating Carat with id {} using data: {}", id, dto);
        CaratUpdateResponseDTO response = caratService.update(id, dto);
        log.info("Successfully updated Carat with id: {}", id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("Deleting Carat with id: {}", id);
        caratService.delete(id);
        log.info("Successfully deleted Carat with id: {}", id);
    }
}
