package com.room.roomservice.domain.room.client

import com.room.roomservice.domain.room.vo.PostResponse
import com.room.roomservice.global.config.FeignConfig
import com.room.roomservice.global.dto.APIResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "post-service", url = "http://localhost:8000", configuration = [FeignConfig::class])
interface PostServiceClient {

    @GetMapping("/post-service/{roomId}/posts")
    fun getPosts(@PathVariable("roomId") roomId: String): APIResponse<List<PostResponse>>
}