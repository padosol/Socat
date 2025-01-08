package socat.postservice.infrastructure.web.dto.request

data class CreatePostDTO(
        private val userId: String,
        private val title: String,
        private val content: String,
)
