package com.crm.order.service.external.impl;

import com.crm.order.entities.dto.request.ProductUpdateRequestDTO;
import com.crm.order.entities.dto.response.ProductResponseDTO;
import com.crm.order.service.external.ProductService;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;

    @Value("${external.api.product.base-url}")
    private String productApiBaseUrl;

    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductResponseDTO update(String id, ProductUpdateRequestDTO dto) {
        String url = productApiBaseUrl + "/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductUpdateRequestDTO> requestEntity = new HttpEntity<>(dto, headers);

        try {
            ResponseEntity<ProductResponseDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    requestEntity,
                    ProductResponseDTO.class
            );

            return response.getBody();
        } catch (HttpStatusCodeException e) {
            System.err.println("Error updating product: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new RuntimeException("Failed to update product", e);
        }
    }
}
