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

    @Around("@annotation(com.example.outsourcingprojects.common.aop.TrackTime)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        log.info("시작 시간: {}", joinPoint.getSignature());

        try {
            return joinPoint.proceed();

        } finally {
            long end = System.currentTimeMillis();
            long elapsed = end - start;

            // 총 실행시간 누적
            long accumulated = totalExecutionTime.addAndGet(elapsed);

            log.info("종료시간: {} ({}ms)", joinPoint.getSignature(), elapsed);
            log.info("총 실행 시간 = {}ms", accumulated);
        }
    }
}