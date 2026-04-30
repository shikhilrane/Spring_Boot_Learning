package com.shikhilrane.aop.aopApp.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ShipmentServiceImplTest {

    @Autowired
    private ShipmentServiceImpl shipmentService;

    // 1. Test method to check orderPackage functionality
    @Test
    void aopTestOrderPackage() {
        String orderString = shipmentService.orderPackage(4L);  // Calls orderPackage method with orderId = 4
        log.info(orderString);                                          // Prints the returned success message in logs
    }

    // 2. Test method to check trackPackage functionality
    @Test
    void aopTestTrackPackage() {
        shipmentService.trackPackage(4L);   // Calls trackPackage method with orderId = 4 which will throw RuntimeException
    }

}