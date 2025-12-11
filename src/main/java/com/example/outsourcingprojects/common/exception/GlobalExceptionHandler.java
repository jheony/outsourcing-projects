package com.example.outsourcingprojects.common.exception;

import com.example.outsourcingprojects.common.model.response.ErrorResponse;
import com.example.outsourcingprojects.common.util.response.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse<ErrorResponse>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        log.error("MethodArgumentNotValidException 발생 : {}", ex.getMessage());

        String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GlobalResponse.fail(message, null));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<GlobalResponse<ErrorResponse>> handleCustomException(CustomException ex) {

        log.error("CustomException 발생 : {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());

        return ResponseEntity
                .status(ex.getErrorCode().getStatus())
                .body(GlobalResponse.fail(ex.getMessage(), errorResponse));
    }
}
