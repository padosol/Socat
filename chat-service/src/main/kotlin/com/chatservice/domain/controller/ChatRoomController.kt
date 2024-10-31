package com.chatservice.domain.controller

import com.chatservice.domain.dto.ChatRoomDTO
import com.chatservice.domain.repository.ChatRoomRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/chat")
class ChatRoomController(
    private val chatRoomRepository: ChatRoomRepository
) {

    // 채팅 리스트 화면
    @GetMapping("/room")
    fun rooms(model: Model): String {
        return "/chat/room"
    }

    // 모든 채팅방 목록
    @GetMapping("/rooms")
    @ResponseBody
    fun room(): MutableList<ChatRoomDTO> {
        return chatRoomRepository.findAllRoom()
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    fun createRoom(@RequestParam("name") name: String): ChatRoomDTO {
        return chatRoomRepository.createChatRoom(name)
    }

    //채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    fun roomDetail(
        model: Model,
        @PathVariable("roomId") roomId: String
    ): String {
        model.addAttribute("roomId", roomId)
        return "/chat/roomDetail"
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    fun roomInfo(
        @PathVariable("roomId") roomId: String
    ): ChatRoomDTO? {
        return chatRoomRepository.findRoomById(roomId)
    }




}