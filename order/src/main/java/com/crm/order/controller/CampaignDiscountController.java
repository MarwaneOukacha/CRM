package com.crm.order.controller;

import com.crm.order.entities.model.CampaignDiscountDTO;
import com.crm.order.entities.model.CampaignDiscountSearchCriteriaDTO;
import com.crm.order.service.CampaignDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campaign-discounts")
@RequiredArgsConstructor
public class CampaignDiscountController {

    private final CampaignDiscountService campaignDiscountService;

    @PostMapping
    public ResponseEntity<CampaignDiscountDTO> save(@RequestBody CampaignDiscountDTO campaignDiscountDTO) {
        return ResponseEntity.ok(campaignDiscountService.save(campaignDiscountDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampaignDiscountDTO> update(@PathVariable String id,@RequestBody CampaignDiscountDTO campaignDiscountDTO) {
        return ResponseEntity.ok(campaignDiscountService.update(id,campaignDiscountDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampaignDiscountDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(campaignDiscountService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CampaignDiscountDTO> deleteById(@PathVariable String id) {
        return ResponseEntity.ok(campaignDiscountService.deleteById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CampaignDiscountDTO>> search(
            CampaignDiscountSearchCriteriaDTO searchCriteria,
            Pageable pageable
    ) {
        return ResponseEntity.ok(campaignDiscountService.search(searchCriteria, pageable));
    }
}
