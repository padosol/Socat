package com.socat.socatserver.room.controller

import com.socat.socatserver.room.domain.Room
import com.socat.socatserver.room.dto.response.RoomResponse
import com.socat.socatserver.room.service.RoomService
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "room", description = "Room API")
@RestController
class RoomController(
    private val roomService: RoomService,
) {


    // 채팅방 전체조회
    @ApiResponse(
        responseCode = "201",
        description = "Find All Room",
        content = [
            Content(
                mediaType = "application/json"
            )
        ]
    )
    @GetMapping("/rooms")
    fun rooms(): ResponseEntity<List<RoomResponse>> {

        val findAllRoom: List<RoomResponse> = roomService.findAllRoom()

        return ResponseEntity.status(201).body(findAllRoom)
    }

    // 채팅방 상세 조회
    @GetMapping("/room")
    fun getRoom(roomId: String): ResponseEntity<RoomResponse> {

        val findRoom = roomService.findRoom(roomId)

        return ResponseEntity.ok().body(findRoom)
    }

    // 채팅방 생성
    @PostMapping("/room")
    fun create(@RequestParam("name") name: String): ResponseEntity<RoomResponse> {

        val createdRoom = roomService.createRoom(Room(
            roomName = name
        ))

        return ResponseEntity.ok().body(createdRoom)
    }

}