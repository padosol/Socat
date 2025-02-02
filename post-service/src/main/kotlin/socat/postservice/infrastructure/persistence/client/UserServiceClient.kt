package socat.postservice.infrastructure.persistence.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import socat.postservice.infrastructure.config.FeignConfig
import socat.postservice.infrastructure.persistence.vo.UserResponse

@FeignClient(name = "user-service", url = "http://localhost:8000", configuration = [FeignConfig::class])
interface UserServiceClient {

    @GetMapping("/user-service/users/{userId}")
    fun getUser(@PathVariable(value = "userId") userId: String): UserResponse
}