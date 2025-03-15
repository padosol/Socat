package socat.postservice.domain.model

import socat.postservice.domain.vo.CommentStatus
import java.time.LocalDateTime

class Comment(
    val commentId: String,
    val postId: String,
    val userId: String,
    var comment: String,
    var parent: Comment? = null,
    var likes: Int,
    var status: CommentStatus,
    val createdAt: LocalDateTime,
    var viewCount: Int,
    val username: String? = null,
    var updatedAt: LocalDateTime? = null,
    val children: MutableList<Comment> = mutableListOf()
) {

    companion object {
        fun write(
            commentId: String,
            postId: String,
            userId: String,
            comment: String,
            createdAt: LocalDateTime
        ): Comment {
            return Comment(
                commentId = commentId,
                postId = postId,
                userId = userId,
                comment = comment,
                likes = 0,
                status = CommentStatus.READ,
                createdAt = createdAt,
                viewCount = 0
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

    fun reply(parentComment: Comment) {
        this.parent = parentComment
    }

    fun plusViewCount() {
        this.viewCount += 1
    }

}