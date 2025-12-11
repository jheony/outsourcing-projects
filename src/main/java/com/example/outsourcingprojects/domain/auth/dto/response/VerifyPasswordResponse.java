package com.example.outsourcingprojects.domain.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VerifyPasswordResponse {

    private final boolean isValid;
}
