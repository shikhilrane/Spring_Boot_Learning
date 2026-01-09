package com.exceptionHandling.ExceptionHandling.advices;

import com.exceptionHandling.ExceptionHandling.exceptions.ResourceNotFoundExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 1. If we get any ResourceNotFoundException type of exception then it will be handled by this method
    @ExceptionHandler(ResourceNotFoundExeption.class)     // Handles exceptions of type NoSuchElementException
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundExeption exception){
        ApiError apiError = ApiError.builder()                          // Creates an ApiError object using Lombok's builder pattern with NOT_FOUND status and message
                .status(HttpStatus.NOT_FOUND)                           // Sets HTTP status to 404
                .message(exception.getMessage())                        // Gets a custom error message
                .build();                                               // Builds the ApiError object
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);    // Returns the ApiError wrapped in a ResponseEntity with 404 status
    }

    // 2. If we get any other type of exception then it will be handled by this method
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleInternalServerError(Exception exception){
        ApiError apiError = ApiError.builder()                          // Creates an ApiError object using Lombok's builder pattern with NOT_FOUND status and message
                .status(HttpStatus.INTERNAL_SERVER_ERROR)               // Sets HTTP status to 404
                .message(exception.getMessage())                        // Gets a custom error message
                .build();                                               // Builds the ApiError object
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);    // Returns the ApiError wrapped in a ResponseEntity with 404 status
    }

    // 3. If validation fails then this will be executed
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInputValidationErrors(MethodArgumentNotValidException e){
        List<String> errors = e
                .getBindingResult()                             // If validation fails while binding the request data (only available for Validation)
                .getAllErrors()                                 // Get all those errors
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()                          // Creates an ApiError object using Lombok's builder pattern with NOT_FOUND status and message
                .status(HttpStatus.BAD_REQUEST)                         // Sets HTTP status to 404
                .message("Input validation failed")                     // Gets a custom error message
                .subErrors(errors)
                .build();                                               // Builds the ApiError object
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);  // Returns the ApiError wrapped in a ResponseEntity with 404 status
    }
}

// GlobalExceptionHandler ->  Handles NoSuchElementException globally and returns a custom JSON error response using ApiError.

// @RestControllerAdvice -> It combines @ControllerAdvice and @ResponseBody to handle exceptions globally and return JSON responses.

// @ExceptionHandler -> it handles the specified exception (Exception.class here) and returns a simple HTTP response.