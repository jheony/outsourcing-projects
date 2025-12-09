package com.example.outsourcingprojects.domain.user.dto;

import java.time.LocalDateTime;

public class SignUpResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final String name;
    private final int role;
    private final LocalDateTime createdAt;


    public SignUpResponse(Long id, String username, String email, String name, int role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
    }

    public Long getId() {return id;}
    public String getUsername() {return username;}
    public String getEmail() {return email;}
    public String getName() {return name;}
    public int getRole() {return role;}
    public LocalDateTime getCreatedAt() {return createdAt;}
}
