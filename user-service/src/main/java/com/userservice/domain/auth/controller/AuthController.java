package com.userservice.domain.auth.controller;

import com.userservice.domain.auth.dto.request.LoginDto;
import com.userservice.domain.auth.dto.request.TokenDto;
import com.userservice.domain.auth.dto.response.AuthDto;
import com.userservice.domain.auth.service.usecase.AccessUseCase;
import com.userservice.domain.auth.service.usecase.LogoutUseCase;
import com.userservice.domain.auth.service.usecase.RefreshUseCase;
import com.userservice.global.config.security.JwtFilter;
import com.userservice.global.dto.APIResponse;
import com.userservice.global.exception.CustomException;
import com.userservice.global.utils.JwtProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "유저 권한 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshUseCase refreshUseCase;
    private final LogoutUseCase logoutUseCase;
    private final AccessUseCase accessUseCase;
    private final JwtProvider jwtProvider;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthDto> authorize(
            @RequestBody LoginDto loginDto
    ) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = accessUseCase.createAccessToken(authentication);
        String refreshToken = refreshUseCase.createRefreshToken(accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
        
        // refresh token 도 생성해야함
        return ResponseEntity
                .status(200)
                .headers(headers)
                .body(new AuthDto(accessToken, refreshToken));
    }

    @PostMapping("/refresh-auth")
    public ResponseEntity<APIResponse<AuthDto>> refreshAuthorize(
        @RequestBody TokenDto refreshDTO
    ) {
        try {
            AuthDto refresh = refreshUseCase.refresh(refreshDTO.getRefreshToken(), refreshDTO.getAccessToken());

            HttpHeaders headers = new HttpHeaders();
            headers.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + refresh.getAccessToken());

            return ResponseEntity
                    .status(200)
                    .headers(headers)
                    .body(APIResponse.ok(refresh));
        } catch(CustomException e) {
            return ResponseEntity.status(200).body(APIResponse.fail(null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        @RequestBody TokenDto tokenDto
    ) {
        logoutUseCase.logout(tokenDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/accessToken")
    public ResponseEntity<APIResponse<Boolean>> accessTokenAuth(
        HttpServletRequest request
    ) {
        String resolveToken = jwtProvider.resolveToken(request);
        boolean b = jwtProvider.validateToken(resolveToken);

        return ResponseEntity.ok(APIResponse.ok(b));
    }
}
