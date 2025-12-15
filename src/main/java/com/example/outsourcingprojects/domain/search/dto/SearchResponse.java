package com.example.outsourcingprojects.domain.search.dto;

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