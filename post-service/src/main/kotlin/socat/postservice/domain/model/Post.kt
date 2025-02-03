package socat.postservice.domain.model

import socat.postservice.infrastructure.persistence.entity.PostEntity
import socat.postservice.infrastructure.web.dto.request.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.ModifyPostDTO
import socat.postservice.infrastructure.web.dto.response.PostResponse
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class Post(
    val roomId: String,
    val postId: String,
    var title: String,
    var content: String,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime? = null,
    val userId: String,
) {

    companion object {
        fun createPost(createPostDTO: CreatePostDTO): Post {
            return Post(
                roomId = createPostDTO.roomId,
                postId = UUID.randomUUID().toString(),
                title = createPostDTO.title,
                content = createPostDTO.content,
                createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                userId = createPostDTO.userId
            )
        }
    }

    fun toDTO(): PostResponse {
        return PostResponse(
            postId = postId,
            userId = userId,
            title = title,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    fun toEntity(): PostEntity {
        return PostEntity(
            roomId = roomId,
            postId = postId,
            title = title,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt,
            userId = userId,
        )
    }

    fun modify(modifyPostDTO: ModifyPostDTO) {
        title = modifyPostDTO.title
        content = modifyPostDTO.content
        updatedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
    }
}