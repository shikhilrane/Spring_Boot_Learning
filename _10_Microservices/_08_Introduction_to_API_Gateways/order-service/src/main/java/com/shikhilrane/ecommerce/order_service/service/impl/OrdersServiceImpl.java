package com.shikhilrane.ecommerce.order_service.service.impl;

import com.shikhilrane.ecommerce.order_service.clients.InventoryOpenFeignClient;
import com.shikhilrane.ecommerce.order_service.dto.OrderRequestDto;
import com.shikhilrane.ecommerce.order_service.entity.OrderItem;
import com.shikhilrane.ecommerce.order_service.entity.OrderStatus;
import com.shikhilrane.ecommerce.order_service.entity.Orders;
import com.shikhilrane.ecommerce.order_service.repository.OrdersRepository;
import com.shikhilrane.ecommerce.order_service.service.OrdersService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersServiceImpl implements OrdersService {

    private final OrdersRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;

    @Override
//    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
    @RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "createOrderFallback")
//    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "createOrderFallback")
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        log.info("Calling the createOrder method");
        Double totalPrice = inventoryOpenFeignClient.reduceStocks(orderRequestDto); // Calls Inventory Service to reduce stock and calculate total price.

        Orders orders = modelMapper.map(orderRequestDto, Orders.class);             // Converts DTO into Order entity.
        for(OrderItem orderItem: orders.getItems()) {
            orderItem.setOrder(orders);                                             // Sets parent order reference for each order item.
        }
        orders.setTotalPrice(totalPrice);                                           // Sets total order price.
        orders.setOrderStatus(OrderStatus.CONFIRMED);                               // Marks order as confirmed.

        Orders savedOrder = orderRepository.save(orders);                           // Saves order into database.

        return modelMapper.map(savedOrder, OrderRequestDto.class);                  // Converts saved entity back to DTO and returns it.
    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto, Throwable throwable) {
        log.error("Fallback occurred due to : {}", throwable.getMessage());
        return new OrderRequestDto();
    }

    @Override
    @Transactional
    public OrderRequestDto cancelOrder(Long orderId) {
        log.info("Calling cancelOrder method");
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));    // Fetches order from database.
        if(order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order is already cancelled");                               // Checks whether order is already cancelled.
        }
        OrderRequestDto orderRequestDto = modelMapper.map(order, OrderRequestDto.class);            // Converts order entity into DTO.
        inventoryOpenFeignClient.restoreStocks(orderRequestDto);                                    // Calls Inventory Service to restore product stocks.
        order.setOrderStatus(OrderStatus.CANCELLED);                                                // Marks order as cancelled.
        Orders cancelledOrder = orderRepository.save(order);                                        // Saves updated order in database.
        return modelMapper.map(cancelledOrder, OrderRequestDto.class);                              // Converts saved entity back to DTO and returns it.
    }

    @Override
    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all orders");
        List<Orders> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderRequestDto.class)).toList();
    }

    @Override
    public OrderRequestDto getOrderById(Long id) {
        log.info("Fetching order with ID: {}", id);
        Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderRequestDto.class);
    }
}
