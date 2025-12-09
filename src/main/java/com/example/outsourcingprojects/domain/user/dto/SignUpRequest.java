package com.example.outsourcingprojects.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Size(max = 30, message = "username은 30자 이하로 입력해주세요.")
    private final String username;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Size(max = 255, message = "이메일은 30자 이하로 입력해주세요.")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "올바른 이메일 형식이 아닙니다."
    )
    private final String email;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 50, message = "비밀번호는 8자 이상, 50자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$",
            message = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 최소 1개씩 포함해야 합니다."
    )
    private final String password;
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(max = 20, message = "이름은 30자 이하로 입력해주세요.")
    private final String name;

    public SignUpRequest(String username, String email, String password, String name) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
