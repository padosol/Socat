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
import com.room.roomservice.global.dto.APIResponse
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

    /**
     * 방 전체 조회
     */
    @GetMapping("/rooms")
    override fun rooms(): ResponseEntity<APIResponse<List<RoomResponse>>> {
        val findAllRoom: List<Room> = findRoomUseCase.findAllRoom()

        return ResponseEntity.status(200).body(APIResponse.ok(findAllRoom.map { it.toDto() }.toList()))
    }

    /**
     * 방 상세 정보
     */
    @GetMapping("/rooms/{roomId}")
    override fun getRoom(
        @PathVariable(value = "roomId") roomId: String
    ): ResponseEntity<APIResponse<RoomResponse>> {

        val findRoom: Room = findRoomUseCase.findRoomById(roomId)

        return ResponseEntity.ok().body(APIResponse.ok(findRoom.toDto()))
    }

    /**
     * 방 생성
     */
    @PostMapping("/rooms")
    override fun create(
            @RequestBody createRoomDTO: CreateRoomDTO,
            request: HttpServletRequest
    ): ResponseEntity<APIResponse<RoomResponse>> {
        val userId = jwtProvider.getUserIdByRequest(request)

        val createdRoom: Room = createRoomUseCase.createRoom(createRoomDTO, userId)

        return ResponseEntity.status(201).body(APIResponse.ok(createdRoom.toDto()))
    }

    /**
     * 방 삭제
     */
    @DeleteMapping("/rooms")
    override fun delete(
            @RequestBody removeRoomDTO: RemoveRoomDTO,
            request: HttpServletRequest
    ): ResponseEntity<Void> {
        val userId = jwtProvider.getUserIdByRequest(request)

        removeRoomUseCase.remove(removeRoomDTO.roomId, userId)

        return ResponseEntity.status(204).build()
    }

    /**
     * 방 수정
     */
    @PutMapping("/rooms")
    override fun modify(
            @RequestBody modifyRoomDTO: ModifyRoomDTO,
            request: HttpServletRequest
    ): ResponseEntity<APIResponse<RoomResponse>> {
        val userId = jwtProvider.getUserIdByRequest(request)

        val modifiedRoom: Room = modifyRoomUseCase.modify(modifyRoomDTO, userId)

        return ResponseEntity.status(200).body(APIResponse.ok(modifiedRoom.toDto()))
    }

}