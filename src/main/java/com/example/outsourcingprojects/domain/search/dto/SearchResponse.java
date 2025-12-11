package com.example.outsourcingprojects.domain.search.dto;

import com.example.outsourcingprojects.domain.task.dto.SearchTaskResponse;
import com.example.outsourcingprojects.domain.team.dto.response.SearchTeamResponse;
import com.example.outsourcingprojects.domain.user.dto.response.SearchUserResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SearchResponse {

    private final List<SearchTaskResponse> tasks;

    private final List<SearchTeamResponse> teams;

    private final List<SearchUserResponse> users;

}
