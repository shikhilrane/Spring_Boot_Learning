package com.shikhilrane.shikhil.prod_ready_features.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostAuditDto {

    private Long id;
    private String title;
    private String description;
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime updatedDate;
}