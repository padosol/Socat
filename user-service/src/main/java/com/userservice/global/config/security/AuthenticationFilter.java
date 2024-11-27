package com.userservice.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.domain.user.controller.dto.response.UserResponse;
import com.userservice.domain.user.service.usecase.GetUserUseCase;
import com.userservice.global.dto.LoginDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Environment env;
    private final GetUserUseCase getUserUseCase;

    public AuthenticationFilter(
            AuthenticationManager authenticationManager,
            GetUserUseCase userService,
            Environment env
    ) {
        super.setAuthenticationManager(authenticationManager);
        this.getUserUseCase = userService;
        this.env = env;
    }


    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {

        try {
            LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword(),
                            new ArrayList<>()
                    )
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {

        String email = ((User) authResult.getPrincipal()).getUsername();
        UserResponse userResponse = getUserUseCase.findUserByEmail(email);

        String jwt = Jwts.builder()
                .subject(userResponse.getId())
                .expiration(
                        new Date(System.currentTimeMillis()
                        + Long.parseLong(env.getProperty("jwt.access-token-expired-time")))
                )
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(env.getProperty("jwt.secret"))))
                .compact();

        response.addHeader(HttpHeaders.AUTHORIZATION, jwt);
    }
}
