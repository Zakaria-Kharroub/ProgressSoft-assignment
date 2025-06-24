package com.progressoft.assignment.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AspectMonitoringService {

    private static final String COLOR_INFO = "\u001B[34m";
    private static final String COLOR_WARN = "\u001B[35m";
    private static final String COLOR_ERR = "\u001B[31m";
    private static final String COLOR_RESET = "\u001B[0m";

    @Before("execution(* com.progressoft.assignment.service.impl.*.*(..))")
    public void onMethodEntry(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        Object[] parameters = joinPoint.getArgs();

        log.info("{}[AOP] Entering: {}{}", COLOR_INFO, method, COLOR_RESET);
        for (int index = 0; index < parameters.length; index++) {
            log.debug("{}[AOP] --> Arg {}: {}{}", COLOR_WARN, index + 1, parameters[index], COLOR_RESET);
        }
    }

    @AfterReturning(
        pointcut = "execution(* com.progressoft.assignment.service.impl.*.*(..))",
        returning = "output"
    )
    public void onMethodSuccess(JoinPoint joinPoint, Object output) {
        String method = joinPoint.getSignature().toShortString();
        log.info("{}[AOP] Completed: {}{}", COLOR_INFO, method, COLOR_RESET);
        log.debug("[AOP] --> Result: {}", output);
    }

    @AfterThrowing(
        pointcut = "execution(* com.progressoft.assignment.service.impl.*.*(..))",
        throwing = "ex"
    )
    public void onMethodException(JoinPoint joinPoint, Throwable ex) {
        String method = joinPoint.getSignature().toShortString();
        log.error("{}[AOP ERROR] Exception in: {}{}", COLOR_ERR, method, COLOR_RESET);
        log.error("[AOP ERROR] --> Message: {}", ex.getMessage(), ex);
    }
}
