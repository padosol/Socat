package com.community.communityservice.domain.community.client

import com.community.communityservice.domain.community.vo.UserResponse
import com.community.communityservice.global.config.FeignConfig
import com.community.communityservice.global.dto.APIResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "user-service", url = "http://localhost:8000", configuration = [FeignConfig::class])
interface UserServiceClient {

    @GetMapping("/user-service/users/{userId}")
    fun getUser(@PathVariable(value = "userId") userId: String): APIResponse<UserResponse>
}