package com.shikhilrane.jpaTutorial.jpaTut.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "product_table",           // Sets the table name in the database. (Optional. If not provided then it will use entity class name)
//        catalog = "product_catalog",    // Specifies the database catalog (rarely used in real projects).
//        schema = "hr",                  // Specifies the database schema where the table will be created.
        uniqueConstraints = {
                @UniqueConstraint(name = "sku_unique", columnNames = {"sku"}),  // Every "sku" must be unique
                @UniqueConstraint(name = "title_price_unique", columnNames = {"title_x", "price"})    // can't add two or more title with same price
        },
        indexes = {
                @Index(name = "sku_index", columnList = "sku")  // Creates an index on SKU to make searches faster.
        }
)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String sku;

    @Column(name = "title_x")
    private String title;

    private BigDecimal price;
    private Integer quantity;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
