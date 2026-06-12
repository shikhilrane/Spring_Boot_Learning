package com.shikhilrane.ecommerce.inventory_service.controller;

import com.shikhilrane.ecommerce.inventory_service.clients.OrdersFeignClient;
import com.shikhilrane.ecommerce.inventory_service.dto.OrderRequestDto;
import com.shikhilrane.ecommerce.inventory_service.dto.ProductDto;
import com.shikhilrane.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;  // Used to talk with Eureka Server and find registered microservices.
    private final RestClient restClient;            // Used to make HTTP calls to other microservices.
    private final OrdersFeignClient ordersFeignClient;

    // Method that fetches data from Order Service.
    @GetMapping("/fetchOrders")
    public String fetchFromOrderService(){
        return ordersFeignClient.helloOrders();
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllInventory() {
        List<ProductDto> inventories = productService.getAllInventory();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getInventoryById(@PathVariable Long id) {
        ProductDto inventory = productService.getProductById(id);
        return ResponseEntity.ok(inventory);
    }

    // API to reduce stock quantity of ordered products.
    @PutMapping("reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto) {
        Double totalPrice = productService.reduceStocks(orderRequestDto);   // Calls service layer to reduce stock and calculate total price.
        return ResponseEntity.ok(totalPrice);                               // Returns total price of all ordered products.
    }

    // API to restore stock quantity when an order is cancelled.
    @PutMapping("restore-stocks")
    public ResponseEntity<Void> restoreStocks(@RequestBody OrderRequestDto orderRequestDto) {
        productService.restoreStocks(orderRequestDto); // Calls service layer to restore stocks.
        return ResponseEntity.ok().build();            // Returns success response.
    }
}
