package com.room.roomservice.global.config

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class CircuitBreakerConfig {

    @Bean
    fun circuitBreakerRegistry(): CircuitBreakerRegistry {
        return CircuitBreakerRegistry.of(configurationCircuitBreaker())
    }

    private fun configurationCircuitBreaker(): CircuitBreakerConfig {
        return CircuitBreakerConfig.custom()
            .failureRateThreshold(40F) // 실패율 임계값
            .waitDurationInOpenState(Duration.ofMillis(10000)) // open -> Half-Open 으로 전환되지 전 대기 시간
            .permittedNumberOfCallsInHalfOpenState(3) // Half-Open 상태에서 허용되는 호출 수
            .slidingWindowSize(10) // 슬라이딩 윈도우 크기
            .recordExceptions(RuntimeException::class.java)
            .build()
    }

}