package socat.postservice.infrastructure.web.dto.request

data class ModifyPostDTO(
        private val postId: String,
        private val title: String,
        private val content: String,
)
