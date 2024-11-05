package com.zlatoust.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LoggingRequestDurationAspect {
    @Around("execution(* com.zlatoust.services.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        if (method.isAnnotationPresent(ExcludeFromLogging.class)) {
            return joinPoint.proceed();
        }

        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTimeMillis = System.currentTimeMillis() - start;

        log.info("Executed in " + formatTime(executionTimeMillis) + " - " + joinPoint.getSignature());

        return proceed;
    }

    public static String formatTime(long millis) {
        double totalSeconds = (double) millis / 1000;
        double remainingSeconds = totalSeconds % 60;

        return String.format("%.3f sec", remainingSeconds);
    }


}

