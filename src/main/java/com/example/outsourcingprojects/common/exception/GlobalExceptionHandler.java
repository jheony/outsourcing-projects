package com.example.outsourcingprojects.common.exception;

import com.example.outsourcingprojects.common.model.response.ErrorResponse;
import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GlobalResponse<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        log.error("MethodArgumentNotValidException 발생 : {} ", ex.getMessage());

        String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();

        return GlobalResponse.fail(message, new ErrorResponse(ex.getStatusCode(), message));
    }

    @ExceptionHandler(value = CustomException.class)
    public GlobalResponse<ErrorResponse> handlerCustomException(CustomException ex) {

        log.error("CustomException 발생 : {} ", ex.getMessage());

        return GlobalResponse.fail(ex.getMessage(), new ErrorResponse(ex.getErrorCode()));
    }
}