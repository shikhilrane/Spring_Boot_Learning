package com.shikhilrane.aop.aopApp.services.impl;

import com.shikhilrane.aop.aopApp.aspects.MyLogging;
import com.shikhilrane.aop.aopApp.services.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ShipmentServiceImpl implements ShipmentService {

    // 1. Method to process an order and return a success message
    @Override
    @MyLogging
    public String orderPackage(Long orderId) {
        try {
            log.info("Processing the order...");                                // Print log that order processing has started
            Thread.sleep(1000);                                           // Pause the thread for 1 second to simulate processing time
        } catch (InterruptedException e) {                                      // This block runs if another thread interrupts this thread while it is sleeping
            log.error("Error occurred while processing the order", e);          // Log the error if interruption happens
        }
        return "Order has been processed successfully, orderId: " + orderId;    // Return success message with orderId
    }

    // 2. Method to track the package of an order
    @Override
    @Transactional      // Use to target by Annotation kind Pointcut
    public String trackPackage(Long orderId) {
        try {
            log.info("Tracking the order...");                                      // Print log that tracking has started
            Thread.sleep(500);                                                // Pause the thread for half second to simulate tracking delay
            throw new RuntimeException("Exception occurred during trackPackage");   // Intentionally throw exception to simulate error(used to demonstrate exception handling in AOP.)
        } catch (InterruptedException e) {                                          // This block runs if another thread interrupts this thread while it is sleeping
            throw new RuntimeException(e);                                          // Convert InterruptedException into RuntimeException and throw it
        }
    }
}
