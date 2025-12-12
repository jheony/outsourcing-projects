package com.example.outsourcingprojects.domain.team.repository;

import com.example.outsourcingprojects.domain.search.dto.SearchTeamResponse;

import java.util.List;

public interface TeamRepositoryCustom {
    List<SearchTeamResponse> getSearchTeams(String query);
}