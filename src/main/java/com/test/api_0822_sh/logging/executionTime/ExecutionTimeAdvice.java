package com.test.api_0822_sh.logging.executionTime;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * AOP log configuration for methods processing time calculation
 */
@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enable:true}")
public class ExecutionTimeAdvice {

    /**
     * Calculate the processing time of this application's methods
     *
     * @param joinPoint joint point for @interface TrackExecutionTime
     * @return object
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("@annotation(com.test.api_0822_sh.logging.executionTime.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Class Name: " + joinPoint.getSignature().getDeclaringTypeName() + ". Method Name: " + joinPoint.getSignature().getName() + ". Time taken for Execution is : " + (endTime - startTime) + "ms");
        return object;
    }

}
