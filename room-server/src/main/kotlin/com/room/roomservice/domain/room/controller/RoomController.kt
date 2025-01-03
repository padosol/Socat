package com.room.roomservice.domain.room.controller

import com.room.roomservice.domain.room.domain.Room
import com.room.roomservice.domain.room.dto.request.CreateRoomDTO
import com.room.roomservice.domain.room.dto.request.ModifyRoomDTO
import com.room.roomservice.domain.room.dto.request.RemoveRoomDTO
import com.room.roomservice.domain.room.dto.response.RoomResponse
import com.room.roomservice.domain.room.service.usecase.CreateRoomUseCase
import com.room.roomservice.domain.room.service.usecase.FindRoomUseCase
import com.room.roomservice.domain.room.service.usecase.ModifyRoomUseCase
import com.room.roomservice.domain.room.service.usecase.RemoveRoomUseCase
import com.room.roomservice.global.jwt.JwtProvider
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RoomController(
        private val findRoomUseCase: FindRoomUseCase,
        private val createRoomUseCase: CreateRoomUseCase,
        private val modifyRoomUseCase: ModifyRoomUseCase,
        private val removeRoomUseCase: RemoveRoomUseCase,
        private val jwtProvider: JwtProvider
) : SwaggerRoomController{

    @GetMapping("/rooms")
    override fun rooms(): ResponseEntity<List<RoomResponse>> {
        val findAllRoom: List<Room> = findRoomUseCase.findAllRoom()

        return ResponseEntity.status(200).body(findAllRoom.map { it.toDto() }.toList())
    }

    @GetMapping("/rooms/{roomId}")
    override fun getRoom(
        @PathVariable(value = "roomId") roomId: String
    ): ResponseEntity<RoomResponse> {

        val findRoom: Room = findRoomUseCase.findRoom(roomId)

        return ResponseEntity.ok().body(findRoom.toDto())
    }


    @PostMapping("/rooms")
    override fun create(
            @RequestBody createRoomDTO: CreateRoomDTO,
            request: HttpServletRequest
    ): ResponseEntity<RoomResponse> {
        val userId = jwtProvider.getUserIdByRequest(request)

        val createdRoom: Room = createRoomUseCase.createRoom(createRoomDTO, userId)

        return ResponseEntity.status(201).body(createdRoom.toDto())
    }

    @DeleteMapping("/rooms")
    override fun delete(
            @RequestBody removeRoomDTO: RemoveRoomDTO,
            request: HttpServletRequest
    ): ResponseEntity<Void> {
        val userId = jwtProvider.getUserIdByRequest(request)

        removeRoomUseCase.remove(removeRoomDTO.roomId, userId)

        return ResponseEntity.status(204).build()
    }

    
    // 채팅방 수정
    @PutMapping("/rooms")
    override fun modify(
            @RequestBody modifyRoomDTO: ModifyRoomDTO,
            request: HttpServletRequest
    ): ResponseEntity<RoomResponse> {
        val userId = jwtProvider.getUserIdByRequest(request)

        val modifiedRoom: Room = modifyRoomUseCase.modify(modifyRoomDTO, userId)

        return ResponseEntity.status(200).body(modifiedRoom.toDto())
    }

}