package com.example.outsourcingprojects.common.aop;

import com.example.outsourcingprojects.common.entity.ActivityLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class ActivityLoggingAspect {

    @Pointcut("@annotation(com.example.outsourcingprojects.common.aop.Loggable)")
    public void ActivityLoggingPointcut() {

    }

    @AfterReturning(value = "ActivityLoggingPointcut()", returning = "response")
    public Object ActivityLoggingAdvice(JoinPoint joinPoint, Object response) throws Throwable {
//
//        Object result = joinPoint.
//        User user;
//        Long taskId;
//        Long commentId;
//        Long type;
//        String description;
//        LocalDateTime timestamp = ;
        //실행 시간, 호출자, 메서드명, 입력 파라미터, 실행 결과, 예외 발생 여부
        ActivityLog activityLog;
        log.info("AOP 성공~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ {}", response);
        return response;
    }
}
