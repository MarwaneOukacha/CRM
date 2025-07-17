package com.crm.order.controller;


import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
//        log.error("Unhandled exception: ", ex);
//        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request);
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
//        log.warn("Bad request: {}", ex.getMessage());
//        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
//        log.info("Entity not found: {}", ex.getMessage());
//        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), request);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
//        log.warn("Validation failed: {}", ex.getMessage());
//
//        List<String> errors = ex.getBindingResult().getFieldErrors()
//                .stream()
//                .map(err -> err.getField() + ": " + err.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Validation failed", errors, request);
//    }
//
//    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message, WebRequest request) {
//        return buildResponseEntity(status, message, null, request);
//    }
//
//    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message, Object errors, WebRequest request) {
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("status", status.value());
//        body.put("error", status.getReasonPhrase());
//        body.put("message", message);
//        body.put("path", request.getDescription(false).replace("uri=", ""));
//        if (errors != null) {
//            body.put("details", errors);
//        }
//        return new ResponseEntity<>(body, status);
//    }
}
