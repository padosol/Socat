package socat.postservice.infrastructure.web.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.multipart.MultipartFile
import socat.postservice.global.dto.APIResponse
import socat.postservice.infrastructure.web.dto.request.post.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.post.ModifyPostDTO
import socat.postservice.infrastructure.web.dto.request.post.RemovePostDTO
import socat.postservice.infrastructure.web.dto.response.post.PostResponse
import socat.postservice.infrastructure.web.dto.response.post.PostWithPage

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

    fun findPostInCommunityByRoomId(
        communityId: String,
        page: Int,
        query: String,
    ): ResponseEntity<APIResponse<PostWithPage>>

    fun fileUpload(
        file: MultipartFile
    ): ResponseEntity<APIResponse<String>>

}