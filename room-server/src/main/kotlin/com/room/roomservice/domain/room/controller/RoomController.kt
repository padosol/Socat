package com.room.roomservice.domain.room.controller

import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.domain.RoomIdGenerator
import com.room.roomservice.domain.room.dto.request.CreateRoomDTO
import com.room.roomservice.domain.room.dto.request.ModifyRoomDTO
import com.room.roomservice.domain.room.dto.request.RemoveRoomDTO
import com.room.roomservice.domain.room.dto.response.RoomResponse
import com.room.roomservice.domain.room.service.RoomService
import com.room.roomservice.domain.room.service.usecase.ModifyRoomUseCase
import com.room.roomservice.domain.room.service.usecase.RemoveRoomUseCase
import com.room.roomservice.global.jwt.JwtProvider
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "room", description = "Room API")
@RestController
class RoomController(
        private val roomService: RoomService,
        private val modifyRoomUseCase: ModifyRoomUseCase,
        private val removeRoomUseCase: RemoveRoomUseCase,
        private val jwtProvider: JwtProvider
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
    fun create(
            @RequestBody createRoomDTO: CreateRoomDTO,
            request: HttpServletRequest
    ): ResponseEntity<RoomResponse> {

        val userId = jwtProvider.getUserIdByRequest(request)

        val createdRoom = roomService.createRoom(
            Room.create(
                userId = userId,
                roomName = createRoomDTO.roomName,
                RoomIdGenerator()
            )
        )

        return ResponseEntity.status(201).body(createdRoom)
    }
    
    // 채팅방 삭제
    @DeleteMapping("/rooms")
    fun delete(
            @RequestBody removeRoomDTO: RemoveRoomDTO,
            httpServletRequest: HttpServletRequest
    ): ResponseEntity<Void> {
        val userId = jwtProvider.getUserIdByRequest(httpServletRequest)

        removeRoomUseCase.remove(removeRoomDTO.roomId, userId)

        return ResponseEntity.status(204).build()
    }

    
    // 채팅방 수정
    @PutMapping("/rooms")
    fun modify(
            @RequestBody modifyRoomDTO: ModifyRoomDTO,
            @RequestHeader("authorization") token: String
    ): ResponseEntity<RoomResponse> {

        val modifiedRoom = modifyRoomUseCase.modify(
                Room(
                        roomId = modifyRoomDTO.roomId,
                        roomName = modifyRoomDTO.roomName,
                        userId = modifyRoomDTO.userId,
                        createAt = modifyRoomDTO.createdAt
                )
        )

        return ResponseEntity.status(200).body(modifiedRoom)
    }

}