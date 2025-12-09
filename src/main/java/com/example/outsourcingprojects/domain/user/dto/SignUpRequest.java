package com.example.outsourcingprojects.domain.user.dto;

public class SignUpRequest {

    private final String username;
    private final String email;
    private final String password;
    private final String name;

    public SignUpRequest(String username, String email, String password, String name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getUsername() {return username;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public String getName() {return name;}
}
