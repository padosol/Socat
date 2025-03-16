package com.apigateway.apigatewayservice.filter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;


@Slf4j
@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    private final HashOperations<String, Object, Object> redisHash;

    Environment env;

    public AuthorizationFilter(Environment env, RedisTemplate<String, Object> redisTemplate) {
        super(Config.class);
        this.env = env;
        this.redisHash = redisTemplate.opsForHash();

    }

    public static class Config {
        private String requiredRole;

        public String getRequiredRole() {
            return requiredRole;
        }

        public void setRequiredRole(String requiredRole) {
            this.requiredRole = requiredRole;
        }
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();


            // 토큰이 존재하는지 확인
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer ", "");

            // 토큰 검증
            log.info("jwt: {}", jwt);
            if (!isJwtValid(jwt)) {
                return onError(exchange, "JWT is not valid", HttpStatus.UNAUTHORIZED);
            }

            if(redisHash.hasKey("black_list", jwt)) {
                return onError(exchange, "JWT is Black", HttpStatus.UNAUTHORIZED);
            }

            String requiredRole = config.getRequiredRole();
            if (StringUtils.hasText(requiredRole)) {
                Claims claims = getClaims(jwt);
                String auths = (String) claims.get("auth");
                List<String> authList = Arrays.stream(auths.split(",")).toList();
                if (!authList.contains(requiredRole)) {
                    return onError(
                            exchange,
                            String.format("%s 권한이 없습니다.", requiredRole),
                            HttpStatus.UNAUTHORIZED
                    );
                }
            }

            return chain.filter(exchange);
        };
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;

        String subject = null;

        try {

            log.info("jwt.secret: {}", env.getProperty("jwt.secret"));

            subject = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(env.getProperty("jwt.secret"))))
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getSubject();

            log.info("subject: {}", subject);

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            returnValue = false;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            returnValue = false;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            returnValue = false;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            returnValue = false;
        }catch(Exception ex) {
            returnValue = false;
        }

        if (subject == null || subject.isEmpty()) {
            returnValue = false;
        }

        return returnValue;
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(env.getProperty("jwt.secret"))))
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    // Mono, Flux -> Spring WebFlux
    private Mono<Void> onError(ServerWebExchange exchange, String errMsg, HttpStatus httpStatus) {

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(errMsg);

        return response.setComplete();
    }

}
