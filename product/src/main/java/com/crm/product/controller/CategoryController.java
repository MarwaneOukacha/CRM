package com.crm.product.controller;
import com.crm.product.entities.dto.SearchCategoryCriteria;
import com.crm.product.entities.dto.request.CategoryAddRequestDTO;
import com.crm.product.entities.dto.request.CategoryUpdateRequestDTO;
import com.crm.product.entities.dto.response.CategoryResponseDTO;
import com.crm.product.entities.dto.response.CategoryUpdateResponseDTO;
import com.crm.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponseDTO create(@RequestBody CategoryAddRequestDTO dto) {
        log.info("Received request to create category: {}", dto);
        return categoryService.create(dto);
    }

    @PutMapping("/{id}")
    public CategoryUpdateResponseDTO update(@PathVariable String id,
                                            @RequestBody CategoryUpdateRequestDTO dto) {
        log.info("Received request to update category with id {}: {}", id, dto);
        return categoryService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        log.info("Received request to delete category with id: {}", id);
        categoryService.delete(id);
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getById(@PathVariable String id) {
        log.info("Fetching category by id: {}", id);
        return categoryService.getById(id);
    }

    @GetMapping
    public Page<CategoryResponseDTO> search(SearchCategoryCriteria criteria, Pageable pageable) {
        log.info("Searching categories with criteria: {}", criteria);
        return categoryService.search(criteria, pageable);
    }
}
