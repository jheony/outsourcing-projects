package com.example.outsourcingprojects.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ActivityLoggingAccept {

    @Pointcut("@annotation(com.example.outsourcingprojects.common.aop.ActivityLogging)")
    public void ActivityLoggingPointcut() {

    }

    @AfterReturning(value = "ActivityLoggingPointcut()", returning = "response")
    public Object ActivityLoggingAdvice(JoinPoint joinPoint, Object response) throws Throwable {

        return response;
    }
}
