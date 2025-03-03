package socat.postservice.domain.model

import java.time.LocalDateTime

class Comment(
    val commentId: String,
    val postId: String,
    val userId: String,
    val comment: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {

}