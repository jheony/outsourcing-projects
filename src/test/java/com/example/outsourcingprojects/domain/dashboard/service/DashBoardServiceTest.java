package com.example.outsourcingprojects.domain.dashboard.service;

import com.example.outsourcingprojects.domain.entity.DashBoard;
import com.example.outsourcingprojects.domain.entity.Task;
import com.example.outsourcingprojects.domain.entity.User;
import com.example.outsourcingprojects.domain.dashboard.dto.DailyTaskDTO;
import com.example.outsourcingprojects.domain.dashboard.dto.DashBoardDTO;
import com.example.outsourcingprojects.domain.dashboard.dto.GetTaskSummaryResponse;
import com.example.outsourcingprojects.domain.dashboard.repository.DashBoardRepository;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DashBoardServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private DashBoardRepository dashBoardRepository;

    @InjectMocks
    private DashBoardService dashBoardService;

    @Test
    @DisplayName("작업 요약 조회 성공")
    void getTaskSummaries_success() {
        //given
        Long userId = 1L;
        User user = new User("username", "email", "password", "name", 10L);
        Task upcomingTask = new Task("title", "description", 10L, 20L, user, LocalDateTime.now().plusDays(1L));
        Task todayTask = new Task("title", "description", 10L, 10L, user, LocalDateTime.now());
        Task overdueTask = new Task("title", "description", 10L, 30L, user, LocalDateTime.now().minusDays(1L));

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        List<Task> upcomingTasks = List.of(upcomingTask);
        List<Task> todayTasks = List.of(todayTask);
        List<Task> overdueTasks = List.of(overdueTask);

        when(taskRepository.findUpcomingTasks(userId, tomorrow)).thenReturn(upcomingTasks);
        when(taskRepository.findTodayTasks(userId, today)).thenReturn(todayTasks);
        when(taskRepository.findOverdueTasks(userId, today)).thenReturn(overdueTasks);

        //when
        GetTaskSummaryResponse result = dashBoardService.getTaskSummaries(userId);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getOverdueTasks().size()).isEqualTo(1);
        assertThat(result.getUpcomingTasks().size()).isEqualTo(1);
        assertThat(result.getTodayTasks().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("주간 작업 조회")
    void getWeeklyTask_success() {

        //given
        Long userId = 1L;
        DailyTaskDTO dailyTaskDTO = new DailyTaskDTO('월', 1L, 10L, "테스트데이트");

        //when
        when(taskRepository.getDailyTask(anyInt(), anyLong())).thenReturn(dailyTaskDTO);

        //then
        assertThat(dashBoardService.getWeeklyTasks(userId)).isNotNull();
        assertThat(dashBoardService.getWeeklyTasks(userId).size()).isEqualTo(7);

    }

    @Test
    @DisplayName("대시보드 조회")
    void getDashBoard_success() {
        //given
        Long userId = 1L;

        DashBoard dashBoard = DashBoard.from(1L, 1L, 1L, 1L, 1L, 1.0, 1.0);
        Long todayMyTasks = 1L;

        when(dashBoardRepository.findLatestDashBoard()).thenReturn(dashBoard);
        when(taskRepository.countMyTaskToday(userId)).thenReturn(todayMyTasks);
        DashBoardDTO dashBoardDTO = DashBoardDTO.from(dashBoard, todayMyTasks);

        //when
        DashBoardDTO result = dashBoardService.getDashBoard(userId);

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(dashBoardDTO);

    }
}

