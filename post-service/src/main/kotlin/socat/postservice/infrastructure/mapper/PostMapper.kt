package socat.postservice.infrastructure.mapper

import jakarta.persistence.ManyToOne
import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.persistence.entity.CategoryEntity
import socat.postservice.infrastructure.persistence.entity.PostEntity
import socat.postservice.infrastructure.web.dto.response.PostResponse
import java.time.LocalDateTime

class PostMapper {

    companion object {
        fun entityToDomain(postEntity: PostEntity): Post {
            return Post(
                roomId = postEntity.roomId,
                postId = postEntity.postId,
                category = CategoryMapper.entityToDomain(postEntity.category),
                title = postEntity.title,
                content = postEntity.content,
                createdAt = postEntity.createdAt,
                updatedAt = postEntity.updatedAt,
                userId = postEntity.userId,
                viewCount = postEntity.viewCount,
                likes = postEntity.likes,
            )
        }

        fun domainToEntity(post: Post): PostEntity {
            return PostEntity(
                postId = post.postId ,
                roomId = post.roomId ,
                category = CategoryMapper.domainToEntity(post.category),
                title = post.title ,
                content = post.content ,
                createdAt = post.createdAt ,
                updatedAt = post.updatedAt ,
                userId = post.userId ,
                viewCount = post.viewCount ,
                likes = post.likes ,
            )
        }

        fun domainToDTO(post: Post, username: String): PostResponse {
            return PostResponse(
                postId = post.postId,
                userId = post.userId,
                title = post.title,
                content = post.content,
                createdAt = post.createdAt,
                updatedAt = post.updatedAt,
                username = username
            )
        }

    }
}