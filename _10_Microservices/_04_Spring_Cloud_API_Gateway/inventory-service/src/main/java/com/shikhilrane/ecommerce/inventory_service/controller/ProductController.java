package com.shikhilrane.ecommerce.inventory_service.controller;

import com.shikhilrane.ecommerce.inventory_service.dto.ProductDto;
import com.shikhilrane.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    // Method that fetches data from Order Service.
    @GetMapping("/fetchOrders")
    public String fetchFromOrderService(){
        ServiceInstance orderService = discoveryClient.getInstances("ORDER-SERVICE").getFirst();    // Gets the address (IP and Port) of Order Service from Eureka.
        return restClient.get()                                                                              // Starts an HTTP GET request.
                .uri(orderService.getUri() + "/orders/core/helloOrders")                                // Creates the URL of the Order Service endpoint.
                .retrieve()                                                                                 // Sends the request and waits for the response.
                .body(String.class);                                                                        // Converts the response into a String and returns it.
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
}
