package socat.postservice.infrastructure.web.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import socat.postservice.domain.service.CommentService
import socat.postservice.global.dto.APIResponse
import socat.postservice.global.utils.JwtProvider
import socat.postservice.infrastructure.mapper.CommentMapper
import socat.postservice.infrastructure.web.dto.request.comment.CreateCommentDTO
import socat.postservice.infrastructure.web.dto.request.comment.ModifyCommentDTO
import socat.postservice.infrastructure.web.dto.request.comment.RemoveCommentDTO
import socat.postservice.infrastructure.web.dto.response.comment.CommentResponse

@RestController
class CommentController(
    private val commentService: CommentService,
    private val jwtProvider: JwtProvider,
) : SwaggerComment{
    override fun createComment(
        createCommentDTO: CreateCommentDTO,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String
    ): ResponseEntity<APIResponse<CommentResponse>> {
        val userId = jwtProvider.getUserIdWithBearer(token)

        val comment = commentService.create(createCommentDTO, userId)

        return ResponseEntity.status(201).body(APIResponse.ok(CommentMapper.domainToResponse(comment)))
    }

    override fun modifyComment(
        modifyCommentDTO: ModifyCommentDTO,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String
    ): ResponseEntity<APIResponse<CommentResponse>> {
        val userId = jwtProvider.getUserIdWithBearer(token)

        val comment = commentService.modify(modifyCommentDTO, userId)

        return ResponseEntity.status(200).body(APIResponse.ok(CommentMapper.domainToResponse(comment)))
    }

    override fun removeComment(
        removeCommentDTO: RemoveCommentDTO,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String
    ): ResponseEntity<APIResponse<Nothing?>> {
        val userId = jwtProvider.getUserIdWithBearer(token)

        commentService.remove(removeCommentDTO.commentId, userId)

        return ResponseEntity.status(204).body(APIResponse.ok(null))
    }

    override fun findCommentById(commentId: String): ResponseEntity<APIResponse<CommentResponse>> {
        val comment = commentService.findByCommentId(commentId)

        return ResponseEntity.ok(APIResponse.ok(CommentMapper.domainToResponse(comment)))
    }

    override fun findAllCommentByPostId(
        postId: String, page: Int, query: String
    ): ResponseEntity<APIResponse<List<CommentResponse>>> {

        val comments = commentService.findAllByPostId(postId)

        return ResponseEntity.ok(APIResponse.ok(comments.map { CommentMapper.domainToResponse(it) }))
    }

}