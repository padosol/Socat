package com.community.communityservice.domain.community.controller

import com.community.communityservice.domain.community.domain.Community
import com.community.communityservice.domain.community.dto.request.CreateCommunityDTO
import com.community.communityservice.domain.community.dto.request.ModifyCommunityDTO
import com.community.communityservice.domain.community.dto.request.RemoveCommunityDTO
import com.community.communityservice.domain.community.dto.response.CommunityResponse
import com.community.communityservice.domain.community.mapper.CommunityMapper
import com.community.communityservice.domain.community.service.community.usecase.CreateCommunityUseCase
import com.community.communityservice.domain.community.service.community.usecase.FindCommunityUseCase
import com.community.communityservice.domain.community.service.community.usecase.ModifyCommunityUseCase
import com.community.communityservice.domain.community.service.community.usecase.RemoveCommunityUseCase
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/communities")
class CommunityController(
    private val createCommunityUseCase: CreateCommunityUseCase,
    private val modifyCommunityUseCase: ModifyCommunityUseCase,
    private val removeCommunityUseCase: RemoveCommunityUseCase,
    private val findCommunityUseCase: FindCommunityUseCase,
    private val jwtProvider: JwtProvider
) : SwaggerController{

    /**
     * 커뮤니티 전체 조회
     */
    @GetMapping()
    override fun communities(
        @RequestParam(name = "type", defaultValue = "all") type: String,
    ): ResponseEntity<APIResponse<List<CommunityResponse>>> {
        val findAllCommunity: List<Community> = findCommunityUseCase.findAll(type)

        return ResponseEntity.status(200).body(APIResponse.ok(findAllCommunity.map { CommunityMapper.domainToDTO(it) }.toList()))
    }

    /**
     * 커뮤니티 상세 정보
     */
    @GetMapping("/{communityId}")
    override fun getCommunity(
        @PathVariable(value = "communityId") communityId: String
    ): ResponseEntity<APIResponse<CommunityResponse>> {

        val findCommunity: Community = findCommunityUseCase.findById(communityId)

        return ResponseEntity.ok().body(APIResponse.ok( CommunityMapper.domainToDTO(findCommunity)))
    }

    /**
     * 커뮤니티 생성
     */
    @PostMapping("")
    override fun create(
        @RequestBody createCommunityDTO: CreateCommunityDTO,
        request: HttpServletRequest
    ): ResponseEntity<APIResponse<CommunityResponse>> {
        val userId = jwtProvider.getUserIdByRequest(request)

        val createdCommunity: Community = createCommunityUseCase.create(createCommunityDTO, userId)

        return ResponseEntity.status(201).body(APIResponse.ok(CommunityMapper.domainToDTO(createdCommunity)))
    }


    /**
     * 커뮤니티 삭제
     */
    @DeleteMapping("/{communityId}")
    override fun delete(
        @PathVariable("communityId") communityId: String,
        request: HttpServletRequest
    ): ResponseEntity<Void> {
        val userId = jwtProvider.getUserIdByRequest(request)

        removeCommunityUseCase.remove(communityId, userId)

        return ResponseEntity.status(204).build()
    }

    /**
     * 커뮤니티 수정
     */
    @PutMapping("")
    override fun modify(
        @RequestBody modifyCommunityDTO: ModifyCommunityDTO,
        request: HttpServletRequest
    ): ResponseEntity<APIResponse<CommunityResponse>> {
        val userId = jwtProvider.getUserIdByRequest(request)

        val modifiedCommunity: Community = modifyCommunityUseCase.modify(modifyCommunityDTO, userId)

        return ResponseEntity.status(200).body(APIResponse.ok( CommunityMapper.domainToDTO(modifiedCommunity) ))
    }

}