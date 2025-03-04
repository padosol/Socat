package socat.postservice.domain.model

import socat.postservice.domain.vo.CommentStatus
import java.time.LocalDateTime

class Comment(
    val commentId: String,
    val postId: String,
    val userId: String,
    var comment: String,
    val parentId: String? = null,
    var likes: Int,
    var status: CommentStatus,
    val createdAt: LocalDateTime,
    val username: String? = null,
    var updatedAt: LocalDateTime? = null,
) {

    companion object {
        fun write(
            commentId: String,
            postId: String,
            userId: String,
            comment: String,
            parentId: String?,
            createdAt: LocalDateTime
        ): Comment {
            return Comment(
                commentId = commentId,
                postId = postId,
                userId = userId,
                comment = comment,
                likes = 0,
                parentId = parentId,
                status = CommentStatus.READ,
                createdAt = createdAt,
            )
        }
    }

    fun isWriter(userId: String): Boolean {
        return this.userId == userId
    }

    fun modifyComment(comment: String, now: LocalDateTime) {
        this.comment = comment
        this.updatedAt = now
    }

    fun remove() {
        this.status = CommentStatus.DELETE
    }

}