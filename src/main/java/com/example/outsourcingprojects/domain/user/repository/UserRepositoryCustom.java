package com.example.outsourcingprojects.domain.user.repository;

import com.example.outsourcingprojects.common.entity.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> getUsersByTeam(Long teamId);
}
