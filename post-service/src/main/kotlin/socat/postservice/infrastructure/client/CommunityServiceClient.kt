package socat.postservice.infrastructure.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import socat.postservice.global.dto.APIResponse
import socat.postservice.infrastructure.config.FeignConfig
import socat.postservice.infrastructure.vo.CategoryResponse

@FeignClient(name = "community-service", url = "http://localhost:8000", configuration = [FeignConfig::class])
interface CategoryServiceClient {

    /**
     * 커뮤니티 Id 로 커뮤니티 조회
     */
    @GetMapping("/community-service/communities/{communityId}")
    fun getCommunity(@PathVariable("communityId") roomId: String): APIResponse<CategoryResponse>
}