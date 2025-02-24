package com.community.communityservice.domain.community.controller

import com.community.communityservice.domain.community.domain.Community
import com.community.communityservice.domain.community.dto.request.CreateCommunityDTO
import com.community.communityservice.domain.community.dto.request.ModifyCommunityDTO
import com.community.communityservice.domain.community.dto.request.RemoveCommunityDTO
import com.community.communityservice.domain.community.dto.response.CommunityResponse
import com.community.communityservice.domain.community.service.usecase.CreateRoomUseCase
import com.community.communityservice.domain.community.service.usecase.FindRoomUseCase
import com.community.communityservice.domain.community.service.usecase.ModifyRoomUseCase
import com.community.communityservice.domain.community.service.usecase.RemoveRoomUseCase
import com.community.communityservice.global.dto.APIResponse
import com.community.communityservice.global.jwt.JwtProvider
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
class CommunityController(
        private val findRoomUseCase: FindRoomUseCase,
        private val createRoomUseCase: CreateRoomUseCase,
        private val modifyRoomUseCase: ModifyRoomUseCase,
        private val removeRoomUseCase: RemoveRoomUseCase,
        private val jwtProvider: JwtProvider
) : SwaggerController{

    /**
     * 방 전체 조회
     */
    @GetMapping("/rooms")
    override fun rooms(): ResponseEntity<APIResponse<List<CommunityResponse>>> {
        val findAllCommunity: List<Community> = findRoomUseCase.findAllRoom()

        return ResponseEntity.status(200).body(APIResponse.ok(findAllCommunity.map { it.toDto() }.toList()))
    }

    /**
     * 방 상세 정보
     */
    @GetMapping("/rooms/{roomId}")
    override fun getRoom(
        @PathVariable(value = "roomId") roomId: String
    ): ResponseEntity<APIResponse<CommunityResponse>> {

        val findCommunity: Community = findRoomUseCase.findRoomById(roomId)

        return ResponseEntity.ok().body(APIResponse.ok(findCommunity.toDto()))
    }

    /**
     * 방 생성
     */
    @PostMapping("/rooms")
    override fun create(
        @RequestBody createCommunityDTO: CreateCommunityDTO,
        request: HttpServletRequest
    ): ResponseEntity<APIResponse<CommunityResponse>> {
        val userId = jwtProvider.getUserIdByRequest(request)

        val createdCommunity: Community = createRoomUseCase.createRoom(createCommunityDTO, userId)

        return ResponseEntity.status(201).body(APIResponse.ok(createdCommunity.toDto()))
    }

    /**
     * 방 삭제
     */
    @DeleteMapping("/rooms")
    override fun delete(
        @RequestBody removeCommunityDTO: RemoveCommunityDTO,
        request: HttpServletRequest
    ): ResponseEntity<Void> {
        val userId = jwtProvider.getUserIdByRequest(request)

        removeRoomUseCase.remove(removeCommunityDTO.roomId, userId)

        return ResponseEntity.status(204).build()
    }

    /**
     * 방 수정
     */
    @PutMapping("/rooms")
    override fun modify(
        @RequestBody modifyCommunityDTO: ModifyCommunityDTO,
        request: HttpServletRequest
    ): ResponseEntity<APIResponse<CommunityResponse>> {
        val userId = jwtProvider.getUserIdByRequest(request)

        val modifiedCommunity: Community = modifyRoomUseCase.modify(modifyCommunityDTO, userId)

        return ResponseEntity.status(200).body(APIResponse.ok(modifiedCommunity.toDto()))
    }

}