package socat.postservice.infrastructure.web.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import socat.postservice.global.dto.APIResponse
import socat.postservice.infrastructure.web.dto.request.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.ModifyPostDTO
import socat.postservice.infrastructure.web.dto.request.RemovePostDTO
import socat.postservice.infrastructure.web.dto.response.PostResponse
import socat.postservice.infrastructure.web.dto.response.PostWithPage

interface SwaggerPostController {
    fun createPost(
            createPostDTO: CreatePostDTO,
            @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<APIResponse<PostResponse>>

    fun modifyPost(
        modifyPostDTO: ModifyPostDTO,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<APIResponse<PostResponse>>

    fun removePost(
        removePostDTO: RemovePostDTO,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<APIResponse<Nothing?>>

    fun findPostById(postId: String): ResponseEntity<APIResponse<PostResponse>>

    fun findAllPost(
        page: Int,
        query: String,
    ): ResponseEntity<APIResponse<List<PostResponse>>>

    fun findPostInRoomByRoomId(
        roomId: String,
        page: Int,
        query: String,
    ): ResponseEntity<APIResponse<PostWithPage>>

    fun fileUpload(
        file: MultipartFile
    ): ResponseEntity<APIResponse<String>>

}