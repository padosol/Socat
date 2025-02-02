package socat.postservice.infrastructure.web.dto.request

data class CreatePostDTO(
        val roomId: String,
        val userId: String,
        val title: String,
        val content: String,
)
