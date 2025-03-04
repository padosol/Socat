package socat.postservice.infrastructure.web.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.multipart.MultipartFile
import socat.postservice.global.dto.APIResponse
import socat.postservice.infrastructure.web.dto.request.comment.CreateCommentDTO
import socat.postservice.infrastructure.web.dto.request.comment.ModifyCommentDTO
import socat.postservice.infrastructure.web.dto.request.comment.RemoveCommentDTO
import socat.postservice.infrastructure.web.dto.response.comment.CommentResponse

interface SwaggerComment {

    fun createComment(
        createCommentDTO: CreateCommentDTO,
        token: String,
    ): ResponseEntity<APIResponse<CommentResponse>>

    fun modifyComment(
        modifyCommentDTO: ModifyCommentDTO,
        token: String,
    ): ResponseEntity<APIResponse<CommentResponse>>

    fun removeComment(
        removeCommentDTO: RemoveCommentDTO,
        token: String,
    ): ResponseEntity<APIResponse<Nothing?>>

    fun findCommentById(commentId: String): ResponseEntity<APIResponse<CommentResponse>>

    fun findAllCommentByPostId(
        postId: String,
        page: Int,
        query: String,
    ): ResponseEntity<APIResponse<List<CommentResponse>>>

}