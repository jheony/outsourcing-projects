package com.example.outsourcingprojects.domain.user.repository;

import com.example.outsourcingprojects.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Boolean existsByEmail(String email);

    Optional<User> findByIdAndDeletedAtIsNull(Long id);

    Boolean existsByIdAndDeletedAtIsNull(Long id);

    List<User> findAllByDeletedAtIsNull();

    List<User> findUsersNotInTeam(Long teamId);

    Optional<User> findByUsernameAndDeletedAtIsNull(String username);

    boolean existsByUsername(String email);
}
