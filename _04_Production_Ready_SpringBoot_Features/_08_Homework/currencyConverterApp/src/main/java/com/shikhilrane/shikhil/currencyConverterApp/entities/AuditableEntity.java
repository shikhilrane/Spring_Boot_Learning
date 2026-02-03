package com.shikhilrane.shikhil.currencyConverterApp.entities;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Audited
public class AuditableEntity {
    @CreatedDate
    private LocalDateTime createdDate;  // this will be created at once only

    @LastModifiedDate
    private LocalDateTime updatedDate;  // this will get updated as any changes happens in db from the entity that uses AuditableEntity

    @CreatedBy
    private String createdBy;           // who created this entry in DB

    @LastModifiedBy
    private String lastModifiedBy;
}