package com.shikhilrane.ecommerce.order_service.clients;

import com.shikhilrane.ecommerce.order_service.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", path = "/inventory")          // Connects to Inventory Service through Eureka.
public interface InventoryOpenFeignClient {
    @PutMapping("/products/reduce-stocks")                             // Calls Inventory Service API to reduce stocks.
    Double reduceStocks(@RequestBody OrderRequestDto orderRequestDto); // Sends order details and receives total price.
}