package socat.postservice.application.port.output

import socat.postservice.domain.model.Comment


interface CommentPersistencePort {

    fun save(comment: Comment): Comment

    fun remove(commentId: String)

    fun findByCommentId(commentId: String): Comment?

    fun findAllByPostId(postId: String): List<Comment>
}