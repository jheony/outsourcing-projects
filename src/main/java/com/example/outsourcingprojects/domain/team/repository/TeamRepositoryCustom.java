package com.example.outsourcingprojects.domain.team.repository;

import com.example.outsourcingprojects.domain.team.dto.response.SearchTeamResponse;


import java.util.List;

public interface TeamRepositoryCustom {
    List<SearchTeamResponse> getSearchTeams(String query);
}
