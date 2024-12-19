package com.apigateway.apigatewayservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final Environment environment;

    @GetMapping("/propeties")
    public String healthCheck() {
        return """
                   ===this application with properties===
                   jwt-token-expiration-minutes: %s
                   jwt-token-secret: %s
                   api-test: %s
              """
        .formatted(
            environment.getProperty("jwt.access_token_expired_time"),
            environment.getProperty("jwt.secret"),
            environment.getProperty("api.test")
        );
    }
}
