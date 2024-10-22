package com.room.roomservice.domain.room.client

import com.room.roomservice.domain.room.vo.UserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service")
interface UserServiceClient {

    @GetMapping("/user-service/users/{userId}")
    fun getUser(@PathVariable(value = "userId") userId: String): UserResponse
}