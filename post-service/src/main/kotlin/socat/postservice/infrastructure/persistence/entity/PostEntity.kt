package socat.postservice.infrastructure.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import socat.postservice.domain.model.Post
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
class PostEntity(

        @Id
        val postId: String,
        val roomId: String,
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