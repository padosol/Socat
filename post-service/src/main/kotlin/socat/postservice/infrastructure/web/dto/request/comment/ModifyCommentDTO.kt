package socat.postservice.infrastructure.web.dto.request.comment

data class ModifyCommentDTO(
    val commentId: String,
    val comment: String,
)
