package com.example.outsourcingprojects.domain.user.repository;

import com.example.outsourcingprojects.common.entity.User;
import com.example.outsourcingprojects.domain.search.dto.SearchUserResponse;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> getUsersByTeam(Long teamId);

    List<SearchUserResponse> getSearchUsers(String query);

    List<User> findUsersNotInTeam(Long teamId);

}
