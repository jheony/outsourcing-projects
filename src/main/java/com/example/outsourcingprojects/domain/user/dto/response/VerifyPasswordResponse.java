package com.example.outsourcingprojects.domain.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class VerifyPasswordResponse {

    private final boolean valid;

}
