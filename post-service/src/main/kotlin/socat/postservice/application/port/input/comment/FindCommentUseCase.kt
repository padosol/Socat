package socat.postservice.application.port.input.comment

import socat.postservice.domain.model.Comment

interface FindCommentUseCase {
    fun findByCommentId(commentId: String): Comment

    fun findAllByPostId(postId: String): List<Comment>
}