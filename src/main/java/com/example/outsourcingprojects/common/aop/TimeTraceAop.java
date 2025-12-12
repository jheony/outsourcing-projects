package com.example.outsourcingprojects.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Aspect
@Component
public class TimeTraceAop {

    private final AtomicLong totalExecutionTime = new AtomicLong(0);

    @Around("@annotation(trackTime)")
    public Object trace(ProceedingJoinPoint joinPoint, TrackTime trackTime) throws Throwable {

        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();

        } finally {
            long end = System.currentTimeMillis();
            long elapsed = end - start;

            log.info("작업 메서드: {}", joinPoint.getSignature());
            log.info("실행 소요 시간: {}ms)", elapsed);
        }
    }
}