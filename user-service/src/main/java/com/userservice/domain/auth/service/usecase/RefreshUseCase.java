package com.userservice.domain.auth.service.usecase;

import com.userservice.domain.auth.dto.response.AuthDto;

public interface RefreshUseCase {

    AuthDto refresh(String refreshToken, String accessToken);

    String createRefreshToken(String accessToken);
}
