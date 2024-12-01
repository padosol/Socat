package com.room.roomservice.domain.room.controller

import com.room.roomservice.domain.room.dto.request.CreateRoomDTO
import com.room.roomservice.domain.room.dto.request.ModifyRoomDTO
import com.room.roomservice.domain.room.dto.request.RemoveRoomDTO
import com.room.roomservice.domain.room.dto.response.RoomResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@Tag(name = "room", description = "Room API")
interface SwaggerRoomController {

    @Operation(summary = "채팅방 전체조회", description = "등록된 모든 채팅방을 가져옵니다.")
    @ApiResponse(
            responseCode = "200",
            description = "성공",
            content = [
                Content(
                        mediaType = "application/json"
                )
            ]
    )
    fun rooms(): ResponseEntity<List<RoomResponse>>

    @Operation(summary = "채팅방 조회", description = "roomId 를 통해 채팅방을 조회한다.")
    @ApiResponse(
            responseCode = "200",
            description = "성공",
            content = [
                Content(
                        mediaType = "application/json"
                )
            ]
    )
    fun getRoom(
            roomId: String
    ): ResponseEntity<RoomResponse>

    @Operation(summary = "채팅방 등록", description = "채팅방을 등록한다.")
    @ApiResponse(
            responseCode = "201",
            description = "성공",
            content = [
                Content(
                        mediaType = "application/json"
                )
            ]
    )
    @SecurityRequirement(name = "bearerAuth")
    fun create(
            createRoomDTO: CreateRoomDTO,
            request: HttpServletRequest
    ): ResponseEntity<RoomResponse>

    @Operation(summary = "채팅방 삭제", description = "채팅방을 삭제한다.")
    @ApiResponse(
            responseCode = "204",
            description = "성공",
            content = [
                Content(
                        mediaType = "application/json"
                )
            ]

    )
    @SecurityRequirement(name = "bearerAuth")
    fun delete(
            removeRoomDTO: RemoveRoomDTO,
            request: HttpServletRequest
    ): ResponseEntity<Void>

    @Operation(summary = "채팅방 수정", description = "채팅방을 수정한다.")
    @ApiResponse(
            responseCode = "200",
            description = "성공",
            content = [
                Content(
                        mediaType = "application/json"
                )
            ]
    )
    @SecurityRequirement(name = "bearerAuth")
    fun modify(
            modifyRoomDTO: ModifyRoomDTO,
            request: HttpServletRequest
    ): ResponseEntity<RoomResponse>

}