package socat.postservice.infrastructure.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import socat.postservice.global.dto.APIResponse
import socat.postservice.infrastructure.config.FeignConfig
import socat.postservice.infrastructure.vo.RoomResponse

@FeignClient(name = "room-service", url = "http://localhost:8000", configuration = [FeignConfig::class])
interface RoomServiceClient {

    /**
     * 방 Id 로 방 조회 - Room-service
     */
    @GetMapping("/room-service/rooms/{roomId}")
    fun getRoom(@PathVariable("roomId") roomId: String): APIResponse<RoomResponse>
}