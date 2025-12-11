package com.example.outsourcingprojects.domain.search.service;

import com.example.outsourcingprojects.domain.search.dto.SearchResponse;
import com.example.outsourcingprojects.domain.task.dto.SearchTaskResponse;
import com.example.outsourcingprojects.domain.task.repository.TempTaskRepository;
import com.example.outsourcingprojects.domain.team.dto.response.SearchTeamResponse;
import com.example.outsourcingprojects.domain.team.repository.TeamRepository;
import com.example.outsourcingprojects.domain.user.dto.response.SearchUserResponse;
import com.example.outsourcingprojects.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final TempTaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public SearchResponse search(String query) {

        List<SearchUserResponse> users = userRepository.getSearchUsers(query);
        List<SearchTeamResponse> teams = teamRepository.getSearchTeams(query);
        List<SearchTaskResponse> tasks = taskRepository.getSearchTasks(query);

        return new SearchResponse(tasks,teams,users);
    }
}
