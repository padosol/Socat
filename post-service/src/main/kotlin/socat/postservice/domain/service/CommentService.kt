package socat.postservice.domain.service

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import socat.postservice.application.port.input.comment.CreateCommentUseCase
import socat.postservice.application.port.input.comment.FindCommentUseCase
import socat.postservice.application.port.input.comment.ModifyCommentUseCase
import socat.postservice.application.port.input.comment.RemoveCommentUseCase
import socat.postservice.application.port.output.CommentPersistencePort
import socat.postservice.domain.model.Comment
import socat.postservice.global.dto.APIResponse
import socat.postservice.global.exception.PostException
import socat.postservice.global.exception.PostExceptionCode
import socat.postservice.infrastructure.client.UserServiceClient
import socat.postservice.infrastructure.mapper.CommentMapper
import socat.postservice.infrastructure.mapper.PostMapper
import socat.postservice.infrastructure.persistence.user.UserRepository
import socat.postservice.infrastructure.persistence.user.entity.UserEntity
import socat.postservice.infrastructure.vo.UserResponse
import socat.postservice.infrastructure.web.dto.request.comment.CreateCommentDTO
import socat.postservice.infrastructure.web.dto.request.comment.ModifyCommentDTO
import socat.postservice.infrastructure.web.dto.response.comment.CommentResponse
import socat.postservice.infrastructure.web.dto.response.post.PostResponse
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class CommentService(
    private val commentPersistencePort: CommentPersistencePort,
    private val postService: PostService,
    private val userRepository: UserRepository,
) : CreateCommentUseCase, FindCommentUseCase, ModifyCommentUseCase, RemoveCommentUseCase {

    @Transactional
    override fun create(createCommentDTO: CreateCommentDTO, userId: String): Comment {
        var username: String? = null

        val postUser = userRepository.findById(userId)
        if (postUser.isEmpty) {
            val userResponse = postService.getUserResponse(userId)
            if (!userResponse.success) {
                throw PostException(PostExceptionCode.USER_NOT_FOUND)
            }
            username = userResponse.data?.username
            userRepository.save(UserEntity(userId, username))
        } else {
            val user = postUser.get()
            username = user.userName
        }

        val commentId: String = UUID.randomUUID().toString()
        val createdAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

        val comment = Comment.write(
            commentId = commentId,
            postId = createCommentDTO.postId,
            userId = userId,
            comment = createCommentDTO.comment,
            createdAt = createdAt,
        )

        val parentId = createCommentDTO.parentId
        if (parentId != null && StringUtils.hasText(parentId)) {
            val parentComment = commentPersistencePort.findByCommentId(parentId)
                ?: throw IllegalStateException("존재하지 않는 댓글 입니다.")

            comment.reply(parentComment)
        }

        return commentPersistencePort.save(comment)
    }

    override fun findByCommentId(commentId: String): Comment {
        return commentPersistencePort.findByCommentId(commentId)
            ?: throw IllegalStateException("존재하지 않는 댓글 입니다.")
    }

    override fun findAllByPostId(postId: String): List<Comment> {
        val comments = commentPersistencePort.findAllByPostId(postId)



        return commentPersistencePort.findAllByPostId(postId)
    }

    override fun modify(modifyCommentDTO: ModifyCommentDTO, userId: String): Comment {

        val comment: Comment = commentPersistencePort.findByCommentId(modifyCommentDTO.commentId)
            ?: throw IllegalStateException("존재하지 않는 댓글 입니다.")

        if (!comment.isWriter(userId)) throw IllegalStateException("댓글 작성자가 아닙니다.")

        val updatedAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

        comment.modifyComment(modifyCommentDTO.comment, updatedAt)

        return commentPersistencePort.save(comment)
    }

    override fun remove(commentId: String, userId: String) {

        val comment: Comment = commentPersistencePort.findByCommentId(commentId)
            ?: throw IllegalStateException("존재하지 않는 댓글 입니다.")

        if (!comment.isWriter(userId)) throw IllegalStateException("댓글 작성자가 아닙니다.")

        comment.remove()

        commentPersistencePort.save(comment)
    }

}