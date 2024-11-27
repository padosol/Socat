package com.userservice.domain.auth.service.usecase;

import org.springframework.security.core.Authentication;

public interface AccessUseCase {
    String createAccessToken(Authentication authentication);
}
