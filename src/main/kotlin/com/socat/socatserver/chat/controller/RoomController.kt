package com.socat.socatserver.chat.controller

import com.socat.socatserver.chat.domain.ChatRoomDTO
import com.socat.socatserver.chat.repository.ChatRoomRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RoomController(

    private val chatRoomRepository: ChatRoomRepository
) {

    @GetMapping("/rooms")
    fun rooms(): ResponseEntity<List<ChatRoomDTO>> {
        return ResponseEntity.ok().body(chatRoomRepository.findAllRoom())
    }

    // 채팅방 생성
    @PostMapping("/room")
    fun create(@RequestParam("name") name: String): String {
        val createdRoom: ChatRoomDTO = chatRoomRepository.createChatRoomDTO(name)

        return createdRoom.name!!
    }
    
    // 채팅방 조회
    @GetMapping("/room")
    fun getRoom(roomId: String): ChatRoomDTO? {
        return chatRoomRepository.findRoomById(roomId = roomId)
    }

}