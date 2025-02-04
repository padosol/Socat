package com.room.roomservice.global.config

import feign.RequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Configuration
class FeignConfig {

    @Bean
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor { requestTemplate ->
            val attributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            val request = attributes.request

            val accessToken = request.getHeader("Authorization")?:null

            if (accessToken != null) {
                requestTemplate.header("Authorization", accessToken)
            }
        }
    }
}