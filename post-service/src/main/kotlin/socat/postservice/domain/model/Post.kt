package socat.postservice.domain.model

import socat.postservice.infrastructure.web.dto.request.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.ModifyPostDTO
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class Post(
    val roomId: String,
    val postId: String,
    val category: Category,
    var title: String,
    var content: String,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime? = null,
    val userId: String,
    var viewCount: Int,
    var likes: Int,
    var comments: MutableList<Comment> = mutableListOf(),
) {

    companion object {
        fun createPost(createPostDTO: CreatePostDTO, category: Category, userId: String): Post {
            return Post(
                roomId = createPostDTO.roomId,
                postId = UUID.randomUUID().toString(),
                category = category,
                title = createPostDTO.title,
                content = createPostDTO.content,
                createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                userId = userId,
                viewCount = 0,
                likes = 0,
                comments = mutableListOf()
            )
        }
    }

    fun modify(modifyPostDTO: ModifyPostDTO) {
        title = modifyPostDTO.title
        content = modifyPostDTO.content
        updatedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
    }

    fun plusLikes() {
        likes += 1
    }

    fun minusLikes() {
        likes -= 1
    }

    fun plusViewCount() {
        viewCount += 1
    }

    fun minusViewCount() {
        viewCount -= 1
    }

}