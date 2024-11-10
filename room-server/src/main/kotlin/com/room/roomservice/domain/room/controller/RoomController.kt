package com.room.roomservice.domain.room.controller

import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.domain.RoomIdGenerator
import com.room.roomservice.domain.room.dto.request.CreateRoomDTO
import com.room.roomservice.domain.room.dto.response.RoomResponse
import com.room.roomservice.domain.room.service.RoomService
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "room", description = "Room API")
@RestController
class RoomController(
    private val roomService: RoomService,
) {


    @ApiResponse(
        responseCode = "200",
        description = "채팅방 전체조회",
        content = [
            Content(
                mediaType = "application/json"
            )
        ]
    )
    @GetMapping("/rooms")
    fun rooms(): ResponseEntity<List<RoomResponse>> {

        val findAllRoom: List<RoomResponse> = roomService.findAllRoom()

        return ResponseEntity.status(200).body(findAllRoom)
    }

    @ApiResponse(
        responseCode = "200",
        description = "채팅방 상세조회",
        content = [
            Content(
                mediaType = "application/json"
            )
        ]
    )
    @GetMapping("/rooms/{roomId}")
    fun getRoom(
        @PathVariable(value = "roomId") roomId: String
    ): ResponseEntity<RoomResponse> {

        val findRoom = roomService.findRoom(roomId)

        return ResponseEntity.ok().body(findRoom)
    }

    @ApiResponse(
        responseCode = "201",
        description = "채팅방 생성",
        content = [
            Content(
                mediaType = "application/json"
            )
        ]
    )
    @PostMapping("/rooms")
    fun create(@RequestBody createRoomDTO: CreateRoomDTO): ResponseEntity<RoomResponse> {

        val createdRoom = roomService.createRoom(
            Room.create(
                createRoomDTO.userId,
                createRoomDTO.roomName,
                RoomIdGenerator()
            )
        )

        return ResponseEntity.status(201).body(createdRoom)
    }
    
    // 채팅방 삭제
    
    
    // 채팅방 수정

}