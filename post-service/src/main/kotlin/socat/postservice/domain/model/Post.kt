package socat.postservice.domain.model

import java.time.LocalDateTime


class Post(
    val roomId: String,
    val postId: String,
    val postName: String,
    val postDesc: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val userId: String,
) {
}