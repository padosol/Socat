package socat.postservice.infrastructure.web.dto.request.post

data class CreatePostDTO(
        val roomId: String,
        val categoryId: String,
        val title: String,
        val content: String,
)
