package com.shikhilrane.aop.aopApp.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ValidationAspect {

    @Pointcut("execution(* com.shikhilrane.aop.aopApp.services.impl.*.*(..))")
    public void allServiceMethodsPointCut() {
    }

    // 5.1 @Around (if we put any number lesser than zero then this will run or else it will skip method and return custom message)
    @Around("allServiceMethodsPointCut()")                      // Runs before and after the method and gives control over execution
    public Object validateOrderId(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object args[] = proceedingJoinPoint.getArgs();          // Gets method arguments
        Long orderId = (Long) args[0];                          // Takes first argument as orderId
        if (orderId > 0) return proceedingJoinPoint.proceed();  // If valid, runs the actual method
        return "Cannot call with negative order id";            // If invalid, skips method and returns custom message
    }
}
