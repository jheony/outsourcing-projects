package com.example.outsourcingprojects.domain.search.service;

import com.example.outsourcingprojects.domain.search.dto.SearchResponse;
import com.example.outsourcingprojects.domain.search.dto.SearchTaskResponse;
import com.example.outsourcingprojects.domain.search.dto.SearchTeamResponse;
import com.example.outsourcingprojects.domain.search.dto.SearchUserResponse;
import com.example.outsourcingprojects.domain.task.repository.TaskRepository;
import com.example.outsourcingprojects.domain.team.repository.TeamRepository;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    // 통합 검색
    @Transactional(readOnly = true)
    public SearchResponse search(String query) {

        List<SearchUserResponse> users = userRepository.getSearchUsers(query);
        List<SearchTeamResponse> teams = teamRepository.getSearchTeams(query);
        List<SearchTaskResponse> tasks = taskRepository.getSearchTasks(query);

        return new SearchResponse(tasks,teams,users);
    }
}
