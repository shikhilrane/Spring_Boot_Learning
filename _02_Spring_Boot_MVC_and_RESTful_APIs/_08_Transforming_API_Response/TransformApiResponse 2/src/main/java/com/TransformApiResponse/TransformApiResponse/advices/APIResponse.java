package com.TransformApiResponse.TransformApiResponse.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse <T>{

    @JsonFormat(pattern = "hh:mm:ss dd-MM-YYYY")
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