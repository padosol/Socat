package com.community.communityservice.domain.community.client

import com.community.communityservice.domain.community.vo.PostResponse
import com.community.communityservice.global.config.FeignConfig
import com.community.communityservice.global.dto.APIResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "post-service", url = "http://localhost:8000", configuration = [FeignConfig::class])
interface PostServiceClient {

    @GetMapping("/post-service/{roomId}/posts")
    fun getPosts(@PathVariable("roomId") roomId: String): APIResponse<List<PostResponse>>
}