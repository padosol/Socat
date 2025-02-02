package socat.postservice.infrastructure.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import socat.postservice.domain.model.Post
import java.time.LocalDateTime

@Entity
class PostEntity(
        @Id
        val roomId: String,
        val postId: String,
        val title: String,
        val content: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime? = null,
        val userId: String,
) {

        fun toDomain(): Post {
                return Post(
                        roomId = roomId,
                        postId = postId,
                        title = title,
                        content = content,
                        createdAt = createdAt,
                        updatedAt = updatedAt,
                        userId = userId,
                )
        }
}