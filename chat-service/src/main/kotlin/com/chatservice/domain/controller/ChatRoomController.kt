package com.chatservice.domain.controller

import com.chatservice.domain.dto.ChatRoomDTO
import com.chatservice.domain.repository.ChatRoomRepository
import com.chatservice.domain.service.usecase.CreateRoomUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat")
class ChatRoomController(
    private val chatRoomRepository: ChatRoomRepository,
    private val createRoomUseCase: CreateRoomUseCase
) {

    // 모든 채팅방 목록
    @GetMapping("/rooms")
    fun room(): MutableList<ChatRoomDTO> {
        return chatRoomRepository.findAllRoom()
    }

    // 채팅방 생성
    @PostMapping("/rooms")
    fun createRoom(@RequestParam("name") name: String): ChatRoomDTO {
        return createRoomUseCase.createRoom(name)
    }

    // 특정 채팅방 조회
    @GetMapping("/rooms/{roomId}")
    fun roomInfo(
        @PathVariable("roomId") roomId: String
    ): ChatRoomDTO? {
        val findRoom = chatRoomRepository.findRoomById(roomId)
        return findRoom
    }
}