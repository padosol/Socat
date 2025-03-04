package socat.postservice.infrastructure.web.dto.request.comment

data class CreateCommentDTO(
    val postId: String,
    val comment: String,
    val parentId: String? = null,
)
