package com.example.outsourcingprojects.common.aop;

import com.example.outsourcingprojects.common.entity.ActivityLog;
import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.exception.CustomException;
import com.example.outsourcingprojects.common.exception.ErrorCode;
import com.example.outsourcingprojects.common.model.ActivityType;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.activitylog.repository.ActivityLogRepository;
import com.example.outsourcingprojects.domain.task.dto.CreateTaskResponse;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ActivityLoggingAspect {

    private final ObjectMapper objectMapper;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Pointcut("@annotation(com.example.outsourcingprojects.common.aop.Loggable)")
    public void ActivityLoggingPointcut() {
    }

    @AfterReturning(value = "ActivityLoggingPointcut()", returning = "returnValue")
    public Object ActivityLoggingAdvice(Object returnValue) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ContentCachingRequestWrapper requestWrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);

        String requestURI = requestWrapper.getRequestURI();

        Long userId = (Long) requestWrapper.getAttribute("userId");
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        GlobalResponse result = (GlobalResponse) returnValue;

        String[] msg = result.getMessage().split(" ");
        String target = msg[0].substring(0, 2);
        String activity = msg[1].substring(0, 2);
        String action = target + " " + activity;

        LocalDateTime timestamp = result.getTimestamp();

        String method = requestWrapper.getMethod();
        String[] uri = requestURI.split("/");
        int uriLen = uri.length;

        Long taskId = 0L;
        if (uriLen > 3) {
            taskId = Long.parseLong(uri[3]);
        }

        if (method.equals("POST") && target.equals("작업")) {
            CreateTaskResponse responseDto = (CreateTaskResponse) result.getData();
            taskId = responseDto.getId();
        }

        ActivityType activityType = ActivityType.methodToType(action, method);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(ErrorCode.TASK_NOT_FOUND));

        String description;

        if (method.equals("PATCH")) {

            TaskStatusType taskStatusType = TaskStatusType.toType(task.getStatus());
            TaskStatusType[] taskStatusTypes = TaskStatusType.values();

            int curStatus = (taskStatusTypes[1].equals(taskStatusType)) ? 0 : 1;

            description = activityType.getFormatDescription(taskStatusTypes[curStatus].name(), taskStatusType.name());
        } else {
            description = activityType.getFormatDescription(task.getTitle());
        }

        ActivityLog activityLog = new ActivityLog(
                user,
                taskId,
                activityType.getActivityNum(),
                description,
                timestamp
        );

        activityLogRepository.save(activityLog);

        return returnValue;
    }

    @Around(value = "ActivityLoggingPointcut()")
    public Object InfoLoggingAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ContentCachingRequestWrapper requestWrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);

        String username = requestWrapper.getAttribute("username").toString();
        String inputParam = requestWrapper.getContentAsString();

        boolean isException = false;

        Long startTime = System.currentTimeMillis();
        String resultStr = null;

        try {
            GlobalResponse result = (GlobalResponse) joinPoint.proceed();
            Object resultData = result.getData();
            resultStr = objectMapper.writeValueAsString(resultData);

            return result;

        } catch (Throwable throwable) {

            isException = true;
            log.info("실행 예외 발생: {}", throwable.getMessage());

            throw throwable;
        } finally {
            Long endTime = System.currentTimeMillis();
            Long runTime = endTime - startTime;

            log.info("""
                            실행 시간: {}
                            호출자: {}
                            메서드명: {}
                            입력 파라미터: {}
                            실행 결과: {}
                            실행 예외 발생 여부: {}
                            """
                    , runTime, username, joinPoint.getSignature().getName(), inputParam, resultStr, isException);
        }
    }
}