package socat.postservice.domain.service

import org.springframework.stereotype.Service
import socat.postservice.application.port.input.comment.CreateCommentUseCase
import socat.postservice.application.port.input.comment.FindCommentUseCase
import socat.postservice.application.port.input.comment.ModifyCommentUseCase
import socat.postservice.application.port.input.comment.RemoveCommentUseCase
import socat.postservice.application.port.output.CommentPersistencePort
import socat.postservice.domain.model.Comment
import socat.postservice.infrastructure.client.UserServiceClient
import socat.postservice.infrastructure.web.dto.request.comment.CreateCommentDTO
import socat.postservice.infrastructure.web.dto.request.comment.ModifyCommentDTO
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class CommentService(
    private val commentPersistencePort: CommentPersistencePort,
    private val userServiceClient: UserServiceClient,
    private val postService: PostService,
) : CreateCommentUseCase, FindCommentUseCase, ModifyCommentUseCase, RemoveCommentUseCase {
    override fun create(createCommentDTO: CreateCommentDTO, userId: String): Comment {
        val commentId: String = UUID.randomUUID().toString()
        val createdAt: LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

        val comment = Comment.write(
            commentId = commentId,
            postId = createCommentDTO.postId,
            userId = userId,
            comment = createCommentDTO.comment,
            parentId = createCommentDTO.parentId,
            createdAt = createdAt,
        )

        return commentPersistencePort.save(comment)
    }

    override fun findByCommentId(commentId: String): Comment {
        return commentPersistencePort.findByCommentId(commentId)
            ?: throw IllegalStateException("존재하지 않는 댓글 입니다.")
    }

    override fun findAllByPostId(postId: String): List<Comment> {
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