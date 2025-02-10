package socat.postservice.infrastructure.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import socat.postservice.global.dto.APIResponse
import socat.postservice.infrastructure.config.FeignConfig
import socat.postservice.infrastructure.vo.UserResponse

@FeignClient(name = "user-service", url = "http://localhost:8000", configuration = [FeignConfig::class])
interface UserServiceClient {

    /**
     * 유저 ID 로 유저 조회 - User-service
     */
    @GetMapping("/user-service/users/{userId}")
    fun getUser(@PathVariable(value = "userId") userId: String): APIResponse<UserResponse>
}