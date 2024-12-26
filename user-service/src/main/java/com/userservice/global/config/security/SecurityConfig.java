package com.userservice.global.config.security;


import com.userservice.domain.user.service.usecase.GetUserUseCase;
import com.userservice.global.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService userService;
    private final GetUserUseCase getUserUseCase;
    private final Environment env;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private static final String[] SWAGGER_AUTH_WHITELIST = {
        "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**", "/v3/api-docs",
        "/actuator/**", "/authenticate", "/refresh-auth", "/logout", "/users/test"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
            .csrf(
                    AbstractHttpConfigurer::disable
            )
            .exceptionHandling(exception -> exception
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(request -> request
                    .requestMatchers("/users").permitAll()
                    .requestMatchers(SWAGGER_AUTH_WHITELIST).permitAll()
                    .anyRequest().authenticated()
            )
            .logout(AbstractHttpConfigurer::disable)
            .authenticationManager(authenticationManager)
//            .addFilter(new AuthenticationFilter(authenticationManager, getUserUseCase, env))
            .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

}
