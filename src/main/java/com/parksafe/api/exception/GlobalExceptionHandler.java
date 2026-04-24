package com.parksafe.api.exception;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

     @ExceptionHandler(BusinessException.class)
     public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
          ErrorResponse error = ErrorResponse.builder()
                    .code(ex.getCode())
                    .timestamp(Instant.now())
                    .description(ex.getMessage())
                    .build();
          return ResponseEntity.status(ex.getHttpStatus()).body(error);
     }

     @ExceptionHandler(MethodArgumentNotValidException.class)
     public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

          String description = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(","));

          ErrorResponse error = ErrorResponse.builder()
                    .code("VALIDATION_ERROR")
                    .timestamp(Instant.now())
                    .description(description)
                    .build();
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

     }

     @ExceptionHandler(Exception.class)
     public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
          ErrorResponse error = ErrorResponse.builder()
                    .code("INTERNAL_SERVER_ERROR")
                    .timestamp(Instant.now())
                    .description("Has ocurred a internal server error")
                    .exception(ex.getMessage())
                    .build();

          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
     }

}
