package com.community.communityservice.domain.community.controller

import com.community.communityservice.domain.community.domain.Community
import com.community.communityservice.domain.community.dto.request.CreateCommunityDTO
import com.community.communityservice.domain.community.dto.request.ModifyCommunityDTO
import com.community.communityservice.domain.community.dto.request.RemoveCommunityDTO
import com.community.communityservice.domain.community.dto.response.CommunityResponse
import com.community.communityservice.domain.community.service.usecase.CreateCommunityUseCase
import com.community.communityservice.domain.community.service.usecase.FindCommunityUseCase
import com.community.communityservice.domain.community.service.usecase.ModifyCommunityUseCase
import com.community.communityservice.domain.community.service.usecase.RemoveCommunityUseCase
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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/community")
class CommunityController(
    private val findCommunityUseCase: FindCommunityUseCase,
    private val createCommunityUseCase: CreateCommunityUseCase,
    private val modifyCommunityUseCase: ModifyCommunityUseCase,
    private val removeCommunityUseCase: RemoveCommunityUseCase,
    private val jwtProvider: JwtProvider
) : SwaggerController{

    /**
     * 방 전체 조회
     */
    @GetMapping()
    override fun communities(): ResponseEntity<APIResponse<List<CommunityResponse>>> {
        val findAllCommunity: List<Community> = findCommunityUseCase.findAll()

        return ResponseEntity.status(200).body(APIResponse.ok(findAllCommunity.map { it.toDto() }.toList()))
    }

    /**
     * 방 상세 정보
     */
    @GetMapping("/{communityId}")
    override fun getCommunity(
        @PathVariable(value = "communityId") communityId: String
    ): ResponseEntity<APIResponse<CommunityResponse>> {

        val findCommunity: Community = findCommunityUseCase.findById(communityId)

        return ResponseEntity.ok().body(APIResponse.ok(findCommunity.toDto()))
    }

    /**
     * 방 생성
     */
    @PostMapping("")
    override fun create(
        @RequestBody createCommunityDTO: CreateCommunityDTO,
        request: HttpServletRequest
    ): ResponseEntity<APIResponse<CommunityResponse>> {
        val userId = jwtProvider.getUserIdByRequest(request)

        val createdCommunity: Community = createCommunityUseCase.create(createCommunityDTO, userId)

        return ResponseEntity.status(201).body(APIResponse.ok(createdCommunity.toDto()))
    }

    /**
     * 방 삭제
     */
    @DeleteMapping("")
    override fun delete(
        @RequestBody removeCommunityDTO: RemoveCommunityDTO,
        request: HttpServletRequest
    ): ResponseEntity<Void> {
        val userId = jwtProvider.getUserIdByRequest(request)

        removeCommunityUseCase.remove(removeCommunityDTO.roomId, userId)

        return ResponseEntity.status(204).build()
    }

    /**
     * 방 수정
     */
    @PutMapping("")
    override fun modify(
        @RequestBody modifyCommunityDTO: ModifyCommunityDTO,
        request: HttpServletRequest
    ): ResponseEntity<APIResponse<CommunityResponse>> {
        val userId = jwtProvider.getUserIdByRequest(request)

        val modifiedCommunity: Community = modifyCommunityUseCase.modify(modifyCommunityDTO, userId)

        return ResponseEntity.status(200).body(APIResponse.ok(modifiedCommunity.toDto()))
    }

}