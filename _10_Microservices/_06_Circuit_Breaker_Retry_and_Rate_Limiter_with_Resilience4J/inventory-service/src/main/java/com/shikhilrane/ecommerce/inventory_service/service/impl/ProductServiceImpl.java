package com.shikhilrane.ecommerce.inventory_service.service.impl;

import com.shikhilrane.ecommerce.inventory_service.dto.OrderRequestDto;
import com.shikhilrane.ecommerce.inventory_service.dto.OrderRequestItemDto;
import com.shikhilrane.ecommerce.inventory_service.dto.ProductDto;
import com.shikhilrane.ecommerce.inventory_service.entity.Product;
import com.shikhilrane.ecommerce.inventory_service.repository.ProductRepository;
import com.shikhilrane.ecommerce.inventory_service.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductDto> getAllInventory() {
        log.info("Fetching all inventory items");
        List<Product> inventories = productRepository.findAll();
        return inventories.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    @Override
    public ProductDto getProductById(Long id) {
        log.info("Fetching Product with ID: {}", id);
        Optional<Product> inventory = productRepository.findById(id);
        return inventory.map(item -> modelMapper.map(item, ProductDto.class))
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    @Override
    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        log.info("Reducing the stocks");
        Double totalPrice = 0.0;                                                                // Stores total order price.
        for(OrderRequestItemDto orderRequestItemDto: orderRequestDto.getItems()) {              // Loops through each ordered product.
            Long productId = orderRequestItemDto.getProductId();                                // Gets product id from request.
            Integer quantity = orderRequestItemDto.getQuantity();                               // Gets requested quantity from request.

            // Fetches product from database.
            Product product = productRepository.findById(productId).orElseThrow(() ->
                    new RuntimeException("Product not found with id: "+productId));

            // Checks whether enough stock is available.
            if(product.getStock() < quantity) {
                throw new RuntimeException("Product cannot be fulfilled for given quantity");
            }

            product.setStock(product.getStock()-quantity);                                      // Reduces available stock.
            productRepository.save(product);                                                    // Saves updated stock in database.
            totalPrice += quantity*product.getPrice();                                          // Adds product price to total order price.
        }
        return totalPrice;                                                                      // Returns final order price.
    }
}
