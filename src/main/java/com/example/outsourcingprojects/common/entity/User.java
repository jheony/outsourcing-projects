package com.example.outsourcingprojects.common.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private Long role;

    public User(String username, String email, String password, String name, Long role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public void update(String name, String email) {
        if (name != null) this.name = name;
        if (email != null) this.email = email;
    }

    @JsonGetter("role")
    public String roleToString() {
        if (this.role == 20L) return "USER";
        if (this.role == 10L) return "ADMIN";
        return "UNKNOWN";
    }

}
