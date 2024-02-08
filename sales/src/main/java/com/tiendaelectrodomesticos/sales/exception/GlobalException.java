package com.tiendaelectrodomesticos.sales.exception;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.ExceptionHandler;import org.springframework.web.bind.annotation.RestControllerAdvice;@RestControllerAdvicepublic class GlobalException {    @ExceptionHandler({ResourceNotFoundException.class})    public ResponseEntity<String> processingResourceNotFoundException(ResourceNotFoundException rnfe) {        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());    }    @ExceptionHandler({BadRequestException.class})    public ResponseEntity<String> processingBadRequestException(BadRequestException bre) {        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());    }}