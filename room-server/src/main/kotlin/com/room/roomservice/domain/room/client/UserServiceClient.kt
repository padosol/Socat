package com.room.roomservice.domain.room.client

import com.room.roomservice.domain.room.vo.UserResponse
import com.room.roomservice.global.config.FeignConfig
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service", url = "http://localhost:8000", configuration = [FeignConfig::class])
interface UserServiceClient {

    @GetMapping("/user-service/users/{userId}")
    fun getUser(@PathVariable(value = "userId") userId: String): UserResponse
}