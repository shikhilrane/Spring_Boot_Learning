package com.example.passwordCheck.advices;

import com.example.passwordCheck.exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFound resourceNotFound){
        ApiError build = ApiError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(resourceNotFound.getMessage())
                .build();
        return new ResponseEntity<>(build, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e){
        ApiError build = ApiError.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
        return new ResponseEntity(build, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handlerValidationException(MethodArgumentNotValidException e){
        List<String> error = e
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError build = ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Validation Failed")
                .subErrors(error)
                .build();

        return new ResponseEntity<>(build, HttpStatus.BAD_REQUEST);

    }
}
