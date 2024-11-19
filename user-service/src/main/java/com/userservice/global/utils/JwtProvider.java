package com.userservice.global.utils;

import com.userservice.global.config.security.JwtFilter;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long accessTokenExpiredTime;
    private final long refreshTokenExpiredTime;
    private SecretKey key;

    public String ACCESS_TOKEN_NAME = "default_access_token_name";
    public String REFRESH_TOKEN_NAME = "default_refresh_token_name";

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expired-time}") long accessTokenExpiredTime,
            @Value("${jwt.refresh_token_expired_time}") long refreshTokenExpiredTime,
            @Value("${jwt.access_token_name}") String accessTokenName,
            @Value("${jwt.refresh_token_name}") String refreshTokenName
    ) {
        this.secret = secret;
        this.accessTokenExpiredTime = accessTokenExpiredTime;
        this.refreshTokenExpiredTime = refreshTokenExpiredTime;
        this.ACCESS_TOKEN_NAME = accessTokenName;
        this.REFRESH_TOKEN_NAME = refreshTokenName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);

    }

    public String createJwt(Authentication authentication) {

        // 권한들
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 만료시간 설정
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.accessTokenExpiredTime);

        return Jwts.builder()
                .subject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key)
                .expiration(validity)
                .compact();
    }

    public String generateAccessToken(String userEmail) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.accessTokenExpiredTime);

        return Jwts.builder()
                .subject(userEmail)
                .signWith(key)
                .expiration(validity)
                .compact();
    }

    public String generateRefreshToken() {
        String uuid = UUID.randomUUID().toString();
        Date expirationDate = new Date((new Date()).getTime() + this.refreshTokenExpiredTime);

        return Jwts.builder()
                .subject(uuid)
                .signWith(key)
                .expiration(expirationDate)
                .compact()
                ;
    }

    public Authentication getAuthentication(String token) {

        // 토큰을 이용해서 Claims 만듬
        Claims claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Claims 에 들어있는 권한들을 가져옴
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // 권한 정보를 이용해서 User 객체를 만듬
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }

        return false;
    }


    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtFilter.AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public String getUserId(HttpServletRequest request) {
        String token = resolveToken(request);

        Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

        String subject = claimsJws.getPayload().getSubject();

        return subject;
    }

    public long getRefreshTokenExpiredTime() {
        return refreshTokenExpiredTime;
    }

}
