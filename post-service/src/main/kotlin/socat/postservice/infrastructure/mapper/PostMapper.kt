package socat.postservice.infrastructure.mapper

import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.persistence.post.entity.PostEntity
import socat.postservice.infrastructure.web.dto.response.post.PostResponse

class PostMapper {

    companion object {
        fun entityToDomain(postEntity: PostEntity): Post {
            return Post(
                communityId = postEntity.communityId,
                postId = postEntity.postId,
//                category = CategoryMapper.entityToDomain(postEntity.category),
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
                communityId = post.communityId ,
//                category = CategoryMapper.domainToEntity(post.category),
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
                communityId = post.communityId,
                postId = post.postId,
                userId = post.userId,
                title = post.title,
                content = post.content,
                createdAt = post.createdAt,
                updatedAt = post.updatedAt,
                username = username
            )
        }

        fun domainToDTO(post: Post): PostResponse {
            return PostResponse(
                communityId = post.communityId,
                postId = post.postId,
                userId = post.userId,
                title = post.title,
                content = post.content,
                createdAt = post.createdAt,
                updatedAt = post.updatedAt,
            )
        }

    }
}