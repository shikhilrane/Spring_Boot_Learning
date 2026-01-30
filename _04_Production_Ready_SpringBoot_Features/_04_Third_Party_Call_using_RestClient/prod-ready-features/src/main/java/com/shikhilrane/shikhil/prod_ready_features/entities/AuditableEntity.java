package com.shikhilrane.shikhil.prod_ready_features.entities;

import jakarta.persistence.Column;
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

@MappedSuperclass                               // Makes this class a parent so its fields are shared with all child entities
@EntityListeners(AuditingEntityListener.class)  // Automatically fills created/updated date and user fields
@Getter
@Setter
@Audited                                        // It Automatically keeps a history of changes made to this entity
public class AuditableEntity {

    @Column
    @CreatedDate
    private LocalDateTime createdDate;

    @Column
    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Column
    @CreatedBy
    private String createdBy;

    @Column
    @LastModifiedBy
    private String lastModifiedBy;
}
