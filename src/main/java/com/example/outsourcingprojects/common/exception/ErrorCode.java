package com.example.outsourcingprojects.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호 형식이 올바르지 않습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않습니다."),

    // 401
    UNAUTHORIZED_LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 서비스 입니다."),
    UNAUTHORIZED_WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED_WRONG_EMAIL_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호 또는 이메일이 일치하지 않습니다.");

    // 추후 추가 예정

    private final HttpStatus status;
    private final String message;

}