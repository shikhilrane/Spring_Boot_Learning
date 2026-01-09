package com.TransformApiResponse.TransformApiResponse.advices;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {
    private HttpStatus status;  // status will be returned in error message
    private String message;     // message will be returned in error message
    private List<String> subErrors ;  // Use to show errors nicely
}

// This class will provide errors on API if occurred to user.
// A simple model class that holds HTTP status and message for returning structured error details in API responses.

// @Data generates getters, setters, and other boilerplate;
// @Builder enables the builder pattern for easy object creation.@Builder creates a builder pattern for your class, allowing you to create objects in a readable, step-by-step way like: Employee emp = Employee.builder().name("John").age(30).build();