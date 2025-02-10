package socat.postservice.infrastructure.web.dto.request

data class CreatePostDTO(
        val roomId: String,
        val title: String,
        val content: String,
)
