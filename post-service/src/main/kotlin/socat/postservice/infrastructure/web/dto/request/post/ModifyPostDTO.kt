package socat.postservice.infrastructure.web.dto.request.post

data class ModifyPostDTO(
    val postId: String,
    val title: String,
    val content: String,
)
