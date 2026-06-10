package com.shikhilrane.ecommerce.inventory_service.service;

import com.shikhilrane.ecommerce.inventory_service.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllInventory();
    ProductDto getProductById(Long id);
}
