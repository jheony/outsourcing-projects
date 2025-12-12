package com.example.outsourcingprojects.common.aop;

import com.example.outsourcingprojects.common.entity.ActivityLog;
import com.example.outsourcingprojects.common.entity.Task;
import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.common.model.ActivityType;
import com.example.outsourcingprojects.common.model.TaskStatusType;
import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import com.example.outsourcingprojects.domain.activitylog.repository.ActivityLogRepository;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.team.dto.response.CreateTeamResponseDto;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private TaskRepository taskRepository;

    @Pointcut("@annotation(com.example.outsourcingprojects.common.aop.Loggable)")
    public void ActivityLoggingPointcut() {
    }

    @AfterReturning(value = "ActivityLoggingPointcut()", returning = "retVal")
    public Object ActivityLoggingAdvice(Object retVal) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();

        Long userId = (Long) request.getAttribute("userId");
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("사용자 없음"));

        GlobalResponse result = (GlobalResponse) retVal;

        String[] msg = result.getMessage().split(" ");
        String target = msg[0].substring(0, 2);
        String activity = msg[1].substring(0, 2);
        String action = target + " " + activity;

        LocalDateTime timestamp = result.getTimestamp();

        String method = request.getMethod();
        String[] uri = requestURI.split("/");
        int uriLen = uri.length;

        Long taskId = 0L;
        if (uriLen > 4) {
            taskId = Long.parseLong(uri[3]);
        }

        // 작업 생성 시 작업 아이디 가져오기
        if (method.equals("POST") && target.equals("작업")) {
            CreateTeamResponseDto responseDto = (CreateTeamResponseDto) result.getData();
            taskId = responseDto.getId();
        }

        ActivityType activityType = ActivityType.methodToType(action, method);

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalStateException("작업 없음"));

        String description = activityType.getFormatDescription(task.getTitle());

        // 작업 상태 변경 시 description 설정
        if (method.equals("PATCH")) {

            TaskStatusType taskStatusType = TaskStatusType.toType(task.getStatus());

            TaskStatusType[] taskStatusTypes = TaskStatusType.values();

            int curStatus = (taskStatusTypes[1].equals(taskStatusType)) ? 0 : 1;

            description = activityType.getFormatDescription(taskStatusTypes[curStatus].name(), taskStatusType.name());
        }

        ActivityLog activityLog = new ActivityLog(
                user,
                taskId,
                activityType.getActivityNum(),
                description,
                timestamp
        );

        activityLogRepository.save(activityLog);

        log.info("AOP 성공~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return retVal;
    }
}

//실행 시간, 호출자, 메서드명, 입력 파라미터, 실행 결과, 예외 발생 여부
//@Around()