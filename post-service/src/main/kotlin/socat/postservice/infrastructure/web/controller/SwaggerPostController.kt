package socat.postservice.infrastructure.web.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import socat.postservice.global.dto.APIResponse
import socat.postservice.infrastructure.web.dto.request.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.ModifyPostDTO
import socat.postservice.infrastructure.web.dto.request.RemovePostDTO
import socat.postservice.infrastructure.web.dto.request.SearchPostDTO
import socat.postservice.infrastructure.web.dto.response.PostResponse

interface SwaggerPostController {
    // 등록
    fun createPost(
            createPostDTO: CreatePostDTO,
            @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<APIResponse<PostResponse>>
    // 수정
    fun modifyPost(modifyPostDTO: ModifyPostDTO): ResponseEntity<APIResponse<PostResponse>>
    // 삭제
    fun removePost(removePostDTO: RemovePostDTO): ResponseEntity<APIResponse<Void>>
    // 조회
    fun findPostById(postId: String): ResponseEntity<APIResponse<PostResponse>>
    // 전체조회
    fun findAllPost(searchPostDTO: SearchPostDTO): ResponseEntity<APIResponse<List<PostResponse>>>

}