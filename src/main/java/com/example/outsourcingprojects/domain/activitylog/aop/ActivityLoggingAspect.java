package com.example.outsourcingprojects.domain.activitylog.aop;

import com.example.outsourcingprojects.common.entity.ActivityLog;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.model.ActivityType;
import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.activitylog.repository.ActivityLogRepository;
import com.example.outsourcingprojects.domain.team.dto.response.CreateTeamResponseDto;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
public class ActivityLoggingAspect {
    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Pointcut("@annotation(com.example.outsourcingprojects.domain.activitylog.aop.Loggable)")
    public void ActivityLoggingPointcut() {
    }

    @AfterReturning(value = "ActivityLoggingPointcut()", returning = "retVal")
    public Object ActivityLoggingAdvice(JoinPoint joinPoint, Object retVal) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();

        Long userId = (Long) request.getAttribute("userId");
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("사용자 없음"));
//        log.info("user = {}", user.getName());

        GlobalResponse result = (GlobalResponse) retVal;
//        log.info("result = {}", result);

        String[] msg = result.getMessage().split(" ");
        String target = msg[0].substring(0, 2);
        String activity = msg[1].substring(0, 2);
        String action = target + " " + activity;

        LocalDateTime timestamp = result.getTimestamp();
//        log.info("timestamp = {}", timestamp);

        String method = request.getMethod();
        String[] uri = requestURI.split("/");
        int uriLen = uri.length;

        Long taskId = 0L;
        if (uriLen > 4) {
            taskId = Long.parseLong(uri[3]);
        }

        if (method.equals("POST") && target.equals("작업")) {
            CreateTeamResponseDto responseDto = (CreateTeamResponseDto) result.getData();
            taskId = responseDto.getId();
        }

        Long activityType = ActivityType.methodToType(action, method).getActivityNum();

        ActivityLog activityLog = new ActivityLog(
                user,
                taskId,
                activityType,
                "", // TODO description 생성? 메서드 구현?
                timestamp
        );

        activityLogRepository.save(activityLog);

        log.info("AOP 성공~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return retVal;
    }
}

//실행 시간, 호출자, 메서드명, 입력 파라미터, 실행 결과, 예외 발생 여부
//@Around()