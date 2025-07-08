package com.crm.product.controller;

import com.crm.product.entities.dto.SearchDailySalesCriteria;
import com.crm.product.entities.dto.request.DailySalesRequestDTO;
import com.crm.product.entities.dto.request.DailySalesUpdateRequestDTO;
import com.crm.product.entities.dto.response.DailySalesResponseDTO;
import com.crm.product.entities.dto.response.DailySalesUpdateResponseDTO;
import com.crm.product.service.DailySalesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/daily-sales")
@RequiredArgsConstructor
@Slf4j
public class DailySalesController {

    private final DailySalesService dailySalesService;

    @PostMapping
    public DailySalesResponseDTO create(@RequestBody DailySalesRequestDTO dto) {
        log.info("DailySalesController::create - Creating daily sales entry: {}", dto);
        return dailySalesService.create(dto);
    }

    @GetMapping("/{id}")
    public DailySalesResponseDTO getById(@PathVariable String id) {
        log.info("DailySalesController::getById - Fetching daily sales with id: {}", id);
        return dailySalesService.getById(id);
    }

    @GetMapping
    public Page<DailySalesResponseDTO> search(SearchDailySalesCriteria criteria, Pageable pageable) {
        log.info("DailySalesController::search - Searching daily sales with criteria: {}", criteria);
        return dailySalesService.search(criteria, pageable);
    }

    @PutMapping("/{id}")
    public DailySalesUpdateResponseDTO update(@PathVariable String id, @RequestBody DailySalesUpdateRequestDTO dto) {
        log.info("DailySalesController::update - Updating daily sales with id: {}, data: {}", id, dto);
        return dailySalesService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("DailySalesController::delete - Deleting daily sales with id: {}", id);
        dailySalesService.delete(id);
    }
}
