package com.example.outsourcingprojects.domain.task.service;


import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.domain.task.dto.CreateTaskRequestDto;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository; // User Repository 추가


    public void CreateTask(CreateTaskRequestDto request) {

        // 1. User 엔티티 조회 (dto에 있는 long assigneeId를 활용)

          User assignee = userRepository.findById(request.getAssignedId())
                  .orElseThrow(() -> {
                      // 로그에도 정확한 실패 원인을 기록합니다.
                      log.error("담당자를 찾을 수 없습니다. 요청된 ID: " + request.getAssignedId());

                      throw new IllegalArgumentException("담당자를 찾을 수 없습니다.");
                  });




    }
}
