package socat.postservice.infrastructure.mapper

import socat.postservice.domain.model.Comment
import socat.postservice.infrastructure.persistence.comment.entity.CommentEntity
import socat.postservice.infrastructure.web.dto.response.comment.CommentResponse

class CommentMapper {

    companion object {
        fun domainToEntity(comment: Comment): CommentEntity {
            return CommentEntity(
                commentId = comment.commentId,
                postId = comment.postId,
                userId = comment.userId,
                comment = comment.comment,
                likes = comment.likes,
                status = comment.status,
                createdAt = comment.createdAt,
            )
        }

        fun entityToDomain(commentEntity: CommentEntity): Comment {
            return Comment(
                commentId = commentEntity.commentId,
                postId = commentEntity.postId,
                userId = commentEntity.userId,
                comment = commentEntity.comment,
                likes = commentEntity.likes,
                status = commentEntity.status,
                createdAt = commentEntity.createdAt,
            )
        }

        fun domainToResponse(comment: Comment): CommentResponse {
            return CommentResponse(
                commentId = comment.commentId,
                comment = comment.comment,
                userId = comment.userId,
                username = comment.username,
                status = comment.status.name,
                createdAt = comment.createdAt,
                updatedAt = comment.updatedAt
            )
        }
    }
}