package com.shikhilrane.aop.aopApp.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect         // Used to mark a class as an Aspect in Spring AOP so it can contain advice methods like @Before, @After, etc.
@Component
@Slf4j
public class LoggingAspect {

//  Advice                                      Pointcut
//    v                                            v
    @Before("execution(* com.shikhilrane.aop.aopApp.services.impl.ShipmentServiceImpl.*(..))")  // This advice will run before execution of any method inside ShipmentServiceImpl class
    public void beforeShipmentServicemethod(JoinPoint joinPoint) {          // Method that will execute before the target method runs
        log.info("Before method called : {}", joinPoint.getSignature());    // Logs the method signature (method name + class) before execution
    }

}

// This is central point from which we can handle all the logging related code that happening inside our code