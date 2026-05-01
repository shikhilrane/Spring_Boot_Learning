package com.shikhilrane.aop.aopApp.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect         // Used to mark a class as an Aspect in Spring AOP so it can contain advice methods like @Before, @After, etc.
@Component
@Slf4j
public class LoggingAspect {

    // LECTURE 1 :
//  Advice                                      Pointcut
//    v                                            v
    @Before("execution(* com.shikhilrane.aop.aopApp.services.impl.ShipmentServiceImpl.*(..))")  // This advice will run before execution of any method inside ShipmentServiceImpl class
    public void beforeShipmentServicemethod(JoinPoint joinPoint) {          // Method that will execute before the target method runs
        log.info("Before method called : {}", joinPoint.getSignature());    // Logs the method signature (method name + class) before execution
    }

    // LECTURE 2 :
    // 1. execution kind Pointcut
    // @Before("execution(* orderPackage(..))")                                            // This will run before the method orderPackage() in any class.
    // @Before("execution(* com.shikhilrane.aop.aopApp.services.impl.*.orderPackage(..))") // This will run before orderPackage() method in any class inside services.impl package.
    @Before("execution(* com.shikhilrane.aop.aopApp.services.impl.*.*(..))")         // This will run before any method of any class inside services.impl package.
    public void beforeOrderPackage(JoinPoint joinPoint) {
        log.info("Before called from LoggingAspect kind, {}", joinPoint.getKind());            // Prints the type of join point (for example: method execution)
        log.info("Before called from LoggingAspect signature, {}", joinPoint.getSignature());  // Prints the method name and details of the method that is going to run
    }

    // 2. within kind Pointcut
    // @Before("within(com.shikhilrane.aop.aopApp.services.impl.*)")  // This advice runs before methods of all classes inside com.shikhilrane.aop.aopApp.services.impl package
    @Before("within(com.shikhilrane.aop.aopApp..*)")          // This advice runs before methods inside com.shikhilrane.aop.aopApp package and its sub-packages
    public void beforeServiceImplCalls() {
        log.info("Service Impl calls");                       // Logs a message whenever a method inside that package is called
    }

    // 3. @annotation kind Pointcut
    // @Before("@annotation(org.springframework.transaction.annotation.Transactional)")  // This advice would run before methods annotated with @Transactional
    @Before("@annotation(com.shikhilrane.aop.aopApp.aspects.MyLogging)")                // This advice runs before methods annotated with @MyLogging
    public void beforeTransationalAnnotationCalls() {
        // log.info("Before Transactional Annotation calls");                            // This would log a message before a @Transactional method runs
        log.info("Before MyLogging Annotation calls");                                  // Logs a message before a method with @MyLogging annotation runs
    }

    // 4. Defining Generic Pointcut
    @Pointcut("@annotation(com.shikhilrane.aop.aopApp.aspects.MyLogging) && within(com.shikhilrane.aop.aopApp..*)") // Matches methods that have @MyLogging annotation and are inside aopApp package or its sub-packages
    public void myLoggingAndAopMethodsPointCut() {}

    @After("myLoggingAndAopMethodsPointCut()")                        // This advice runs after methods annotated with @MyLogging
    public void afterTransationalAnnotationCalls() {
        log.info("After MyLogging Annotation calls");                 // Logs a message after the annotated method execution
    }
}

// This is central point from which we can handle all the logging related code that happening inside our code