package com.example.outsourcingprojects.common.aop;

import com.example.outsourcingprojects.common.entity.ActivityLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class ActivityLoggingAspect {

    @Pointcut("@annotation(com.example.outsourcingprojects.common.aop.Loggable)")
    public void ActivityLoggingPointcut() {

    }

    @AfterReturning(value = "ActivityLoggingPointcut()", returning = "retVal")
    public Object ActivityLoggingAdvice(JoinPoint joinPoint, Object retVal) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        String requestMethod  = request.getMethod();

//        User user;
        Long userId = (Long) request.getAttribute("userId");

        log.info(" retVal.toString() = {}",  retVal.toString());
        Object result = retVal.getClass();
        log.info("result = {}",  result);



        log.info("requestURI = {}", requestURI);
        log.info("requestMethod = {}", requestMethod);

        String[] uri = requestURI.split("/");
        String type = uri[2];
        log.info("type = {}", type);
        if(type.startsWith("task")){
            log.info("task");
//        Long taskId;
//        Long type;
        }

        if(type.startsWith("comment")){
            log.info("comment");
//        Long commentId;
//        Long type;
        }

//
//        String description;
//        LocalDateTime timestamp = ;

        //실행 시간, 호출자, 메서드명, 입력 파라미터, 실행 결과, 예외 발생 여부
        ActivityLog activityLog;
        log.info("AOP 성공~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ {}", retVal);
        return  retVal;
    }
}
