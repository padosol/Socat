package com.community.communityservice.domain.community.controller

import com.community.communityservice.domain.community.dto.request.CreateCommunityDTO
import com.community.communityservice.domain.community.dto.request.ModifyCommunityDTO
import com.community.communityservice.domain.community.dto.request.RemoveCommunityDTO
import com.community.communityservice.domain.community.dto.response.CommunityResponse
import com.community.communityservice.global.dto.APIResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity

@Tag(name = "community", description = "Community API")
interface SwaggerController {

    @Operation(summary = "커뮤니티 전체 조회", description = "등록된 모든 커뮤니티를 가져옵니다.")
    @ApiResponse(
            responseCode = "200",
            description = "성공",
            content = [
                Content(
                        mediaType = "application/json"
                )
            ]
    )
    fun communities(type: String): ResponseEntity<APIResponse<List<CommunityResponse>>>

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
    fun getCommunity(
        communityId: String
    ): ResponseEntity<APIResponse<CommunityResponse>>

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
        createCommunityDTO: CreateCommunityDTO,
        request: HttpServletRequest
    ): ResponseEntity<APIResponse<CommunityResponse>>

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
        communityId: String,
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
        modifyCommunityDTO: ModifyCommunityDTO,
        request: HttpServletRequest
    ): ResponseEntity<APIResponse<CommunityResponse>>


}