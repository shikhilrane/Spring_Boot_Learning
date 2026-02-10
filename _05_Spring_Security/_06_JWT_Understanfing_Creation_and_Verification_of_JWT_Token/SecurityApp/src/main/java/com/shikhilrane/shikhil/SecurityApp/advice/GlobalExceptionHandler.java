package com.shikhilrane.shikhil.SecurityApp.advice;

import com.shikhilrane.shikhil.SecurityApp.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException exception){
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .error(exception.getLocalizedMessage())
                .statusCode(HttpStatus.NOT_FOUND)
                .build();
        return ResponseEntity.ok(apiError);
    }
}
