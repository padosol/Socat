package socat.postservice.infrastructure.config

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class Resilience4jConfig {

    @Bean
    fun circuitBreakerRegistry(): CircuitBreakerRegistry {
        val circuitBreakerConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(50f)
            .waitDurationInOpenState(Duration.ofMillis(1000))
            .permittedNumberOfCallsInHalfOpenState(2)
            .slidingWindowSize(2)
            .build()

        return CircuitBreakerRegistry.of(circuitBreakerConfig)
    }
}