package socat.postservice.infrastructure.persistence.post.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import socat.postservice.domain.model.Post
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
class PostEntity(
        @Id
        val postId: String,
        val communityId: String,

//        @ManyToOne
//        val category: CategoryEntity,

        val title: String,
        val content: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime? = null,
        val userId: String,
        val viewCount: Int,
        val likes: Int,
)