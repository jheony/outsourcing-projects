package com.example.outsourcingprojects.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class SignUpRequest {

    @NotBlank(message = "유저이름은 필수 입력 값입니다.")
    @Size(min = 4, max = 20, message = "username은 20자 이하로 입력해주세요.")
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
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
            message = "비밀번호가 올바르지 않습니다."
    )
    private final String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(min = 2, max = 50, message = "이름은 50자 이하로 입력해주세요.")
    private final String name;

}
