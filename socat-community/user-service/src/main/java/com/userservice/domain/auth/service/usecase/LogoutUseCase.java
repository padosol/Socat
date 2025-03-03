package com.userservice.domain.auth.service.usecase;

import com.userservice.domain.auth.dto.request.TokenDto;

public interface LogoutUseCase {
    void logout(TokenDto tokenDto);
}
