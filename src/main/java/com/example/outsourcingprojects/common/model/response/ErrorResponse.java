package com.example.outsourcingprojects.common.model.response;

import com.example.outsourcingprojects.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }
}