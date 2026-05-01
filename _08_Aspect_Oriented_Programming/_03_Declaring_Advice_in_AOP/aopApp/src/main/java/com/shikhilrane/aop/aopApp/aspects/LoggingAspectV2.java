package com.shikhilrane.aop.aopApp.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspectV2 {

    // LECTURE 3 :
    @Pointcut("execution(* com.shikhilrane.aop.aopApp.services.impl.*.*(..))")  // Defines a reusable pointcut for all methods in services.impl package
    public void allServiceMethodsPointCut() {}

    // 1. @Before advice
    @Before("allServiceMethodsPointCut()")                                      // This advice runs before the matched method execution
    public void beforeServiceMethodCalls(JoinPoint joinPoint) {
        log.info("Before advice method call, {}", joinPoint.getSignature());    // Logs method details before execution
    }

    // 2. @After advice
    @After("allServiceMethodsPointCut()")                                // This advice runs after the matched method execution (both success and exception)
    public void afterServiceMethodCalls(JoinPoint joinPoint) {
        log.info("After advice method call, {}", joinPoint.getSignature()); // Logs method details after execution
    }

    // 3. @AfterReturning
    @AfterReturning(value = "allServiceMethodsPointCut()", returning = "returnedObj")  // This advice runs only after successful execution of the matched method
    public void afterReturningServiceMethodCalls(JoinPoint joinPoint, Object returnedObj) {
        log.info("After returning advice method call, {}", joinPoint.getSignature());   // Logs method details after successful execution
        log.info("After returning returned value, {}", returnedObj);                    // Logs the returned value of the method
    }

    // 4. @AfterThrowing
    @AfterThrowing("allServiceMethodsPointCut()")                                   // This advice runs only when the matched method throws an exception
    public void afterServiceMethodCallsThrows(JoinPoint joinPoint) {
        log.info("After throwing advice method call, {}", joinPoint.getSignature());    // Logs method details when an exception occurs
    }

    // 5. @Around
    @Around("allServiceMethodsPointCut()")      // This advice runs before and after the matched method execution
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();            // Captures start time before method execution
        Object returnedValue = proceedingJoinPoint.proceed();   // Calls the actual method
        Long endTime = System.currentTimeMillis();              // Captures end time after method execution

        Long diff = endTime-startTime;                          // Calculates execution time
        log.info("Time taken for {} is {}", proceedingJoinPoint.getSignature(), diff);  // Logs method name and execution time
        return returnedValue;                                   // Returns the original method result
    }

}
