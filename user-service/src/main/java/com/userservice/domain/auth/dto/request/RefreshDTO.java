package com.userservice.domain.auth.dto.request;

import lombok.Getter;

@Getter
public class RefreshDTO {
    private String accessToken;
    private String refreshToken;
}
