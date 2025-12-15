package com.example.outsourcingprojects.domain.search.service;

import com.example.outsourcingprojects.domain.entity.Task;
import com.example.outsourcingprojects.domain.entity.Team;
import com.example.outsourcingprojects.domain.entity.User;
import com.example.outsourcingprojects.domain.search.dto.SearchResponse;
import com.example.outsourcingprojects.domain.search.dto.SearchTaskResponse;
import com.example.outsourcingprojects.domain.search.dto.SearchTeamResponse;
import com.example.outsourcingprojects.domain.search.dto.SearchUserResponse;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.team.repository.TeamRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private SearchService searchService;

    @Test
    @DisplayName("검색 기능 성공")
    void search_success() {
        //given
        User user = new User("username", "email", "password", "name", 20L);
        Task task = new Task("title", "description", 20L, 20L, user, LocalDateTime.now());
        Team team = Team.of("name", "description");

        SearchUserResponse searchUserResponse = SearchUserResponse.from(user);
        SearchTeamResponse searchTeamResponse = SearchTeamResponse.from(team);
        SearchTaskResponse searchTaskResponse = SearchTaskResponse.from(task);

        List<SearchUserResponse> searchUserResponseList = List.of(searchUserResponse);
        List<SearchTeamResponse> searchTeamResponseList = List.of(searchTeamResponse);
        List<SearchTaskResponse> searchTaskResponseList = List.of(searchTaskResponse);

        when(userRepository.getSearchUsers(anyString())).thenReturn(searchUserResponseList);
        when(taskRepository.getSearchTasks(anyString())).thenReturn(searchTaskResponseList);
        when(teamRepository.getSearchTeams(anyString())).thenReturn(searchTeamResponseList);

        //when
        SearchResponse result = searchService.search("name");

        //then
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(SearchResponse.class);


    }
}
