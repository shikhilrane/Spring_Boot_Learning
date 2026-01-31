package com.shikhilrane.shikhil.prod_ready_features.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse<T>{

    @JsonFormat(pattern = "HH:mm:ss dd-MM-yyyy")
    private LocalDateTime timestamp;
    private T data;
    private ApiError error;

    public APIResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public APIResponse(T data) {
        this();
        this.data = data;
    }

    public APIResponse(ApiError error) {
        this();
        this.error = error;
    }
}

// If we get data then error will be null and if we get error then data will be null