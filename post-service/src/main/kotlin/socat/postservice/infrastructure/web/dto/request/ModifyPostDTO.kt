package socat.postservice.infrastructure.web.dto.request

data class ModifyPostDTO(
        val postId: String,
        val title: String,
        val content: String,
)
