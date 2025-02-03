package socat.postservice.global.utils

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.*
import javax.crypto.SecretKey

@Slf4j
@Component
class JwtProvider(
        @Value("\${jwt.secret:secret}") private val secret: String,
        @Value("\${jwt.access-token-expired-time:600}") private val tokenValidityInMilliseconds: Long
) {

    private val log = LoggerFactory.getLogger(this::class.java)
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret))

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(token)

            return true
        } catch(e: MalformedJwtException){
            log.info("잘못된 JWT 서명입니다.")
        } catch(e: ExpiredJwtException) {
            log.info("만료된 JWT 토큰입니다.")
        } catch(e: UnsupportedJwtException) {
            log.info("지원되지 않는 JWT 토큰입니다.")
        } catch(e: IllegalArgumentException) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }

        return false
    }

    fun resolveToken(request: HttpServletRequest): String {
        val bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else throw IllegalStateException("Bearer Token 이 아닙니다.")
    }

    fun getUserIdByRequest(request: HttpServletRequest): String {
        val token = resolveToken(request)
        val claimsJws: Jws<Claims> = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        return claimsJws.payload.subject
    }

    fun getUserId(token: String): String {
        val claimsJws: Jws<Claims> = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        return claimsJws.payload.subject
    }

    fun getUserIdWithBearer(bearerToken: String): String {
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            getUserId(bearerToken.substring(7))
        } else throw IllegalStateException("Bearer Token 이 아닙니다.")
    }




}
