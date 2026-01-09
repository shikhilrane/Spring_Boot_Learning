package com.TransformApiResponse.TransformApiResponse.advices;

import com.TransformApiResponse.TransformApiResponse.exceptions.ResourceNotFoundExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 1. If we get any ResourceNotFoundException type of exception then it will be handled by this method
    @ExceptionHandler(ResourceNotFoundExeption.class)                   // Handles exceptions of type NoSuchElementException
    public ResponseEntity<APIResponse<?>> handleResourceNotFound(ResourceNotFoundExeption exception){
        ApiError apiError = ApiError.builder()                          // Creates an ApiError object using Lombok's builder pattern with NOT_FOUND status and message
                .status(HttpStatus.NOT_FOUND)                           // Sets HTTP status to 404
                .message(exception.getMessage())                        // Gets a custom error message
                .build();                                               // Builds the ApiError object
        return buildErrorResponseEntity(apiError);                      // Create and return the response with error details and HTTP status
    }


    // 2. If we get any other type of exception then it will be handled by this method
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<?>> handleInternalServerError(Exception exception){
        ApiError apiError = ApiError.builder()                          // Creates an ApiError object using Lombok's builder pattern with NOT_FOUND status and message
                .status(HttpStatus.INTERNAL_SERVER_ERROR)               // Sets HTTP status to 404
                .message(exception.getMessage())                        // Gets a custom error message
                .build();                                               // Builds the ApiError object
        return buildErrorResponseEntity(apiError);                      // Create and return the response with error details and HTTP status
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> handleInputValidationErrors(MethodArgumentNotValidException e){
        List<String> errors = e                                         // Extract all validation error messages from the exception
                .getBindingResult()                                     // Get the result of the validation process
                .getAllErrors()                                         // Get all validation errors
                .stream()                                               // Start a stream to process each error
                .map(objectError -> objectError.getDefaultMessage())    // Extract the default error message from each error
                .collect(Collectors.toList());                          // Collect all error messages into a list

        ApiError apiError = ApiError.builder()                          // Creates an ApiError object using Lombok's builder pattern with NOT_FOUND status and message
                .status(HttpStatus.BAD_REQUEST)                         // Sets HTTP status to 404
                .message("Input validation failed")                     // Gets a custom error message
                .subErrors(errors)
                .build();                                               // Builds the ApiError object
        return buildErrorResponseEntity(apiError);                      // Create and return the response with error details and HTTP status
    }

    //
    private ResponseEntity<APIResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new APIResponse<>(apiError), apiError.getStatus());
    }
}

// @GlobalExceptionHandler ->  Handles NoSuchElementException globally and returns a custom JSON error response using ApiError.

// @RestControllerAdvice -> It combines @ControllerAdvice and @ResponseBody to handle exceptions globally and return JSON responses.