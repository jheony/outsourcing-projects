package com.example.outsourcingprojects.common.util;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GlobalResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public static <T> GlobalResponse<T> of(boolean success, String message, T data) {
        return GlobalResponse.<T>builder()
                .success(success)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> GlobalResponse<T> success(String message, T data) {
        return of(true, message, data);
    }

    public static <T> GlobalResponse<T> fail(String message, T data) {
        return of(false, message, data);
    }
}
