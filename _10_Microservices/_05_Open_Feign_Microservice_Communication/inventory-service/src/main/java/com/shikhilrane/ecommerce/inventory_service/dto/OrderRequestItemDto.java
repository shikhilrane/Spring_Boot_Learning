package com.shikhilrane.ecommerce.inventory_service.dto;

import lombok.Data;

@Data
public class OrderRequestItemDto {
    private Long productId;     // Unique id of product.
    private Integer quantity;   // Quantity requested by customer.
}
