package socat.postservice.infrastructure.web.dto.request.post

data class CreatePostDTO(
        val communityId: String,
        val title: String,
        val content: String,
)
