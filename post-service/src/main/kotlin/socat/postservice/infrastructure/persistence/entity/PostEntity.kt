package socat.postservice.infrastructure.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class PostEntity(
        @Id
        val roomId: String,
        val postId: String,
        val postName: String,
        val postDesc: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val userId: String,
) {
}