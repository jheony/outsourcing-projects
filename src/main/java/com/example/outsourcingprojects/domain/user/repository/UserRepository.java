package com.example.outsourcingprojects.domain.user.repository;

import com.example.outsourcingprojects.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,UserRepositoryCustom {
    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByName(String name);

    Optional<User> findByIdAndDeletedAtIsNull(Long id);

    List<User> findAllByDeletedAtIsNull();

    List<User> findUsersNotInTeam(Long teamId);
}
