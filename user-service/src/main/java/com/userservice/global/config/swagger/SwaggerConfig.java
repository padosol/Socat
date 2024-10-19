package com.userservice.global.config.swagger;

import com.userservice.global.config.security.JwtFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_TOKEN_PREFIX = "Bearer";


    @Bean
    @Profile("!Prod") // 운영 환경에서 swagger 비활성화
    public OpenAPI openAPI() {
        String jwtSchemeName = JwtFilter.AUTHORIZATION_HEADER;

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        Components components = new Components()
                .addSecuritySchemes(
                        jwtSchemeName,
                        new SecurityScheme()
                                .name(jwtSchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme(BEARER_TOKEN_PREFIX)
                                .bearerFormat("application/json")
                );

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
