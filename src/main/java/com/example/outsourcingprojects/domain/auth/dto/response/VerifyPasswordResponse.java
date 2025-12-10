package com.example.outsourcingprojects.domain.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VerifyPasswordResponse {

    private boolean isValid;
}
