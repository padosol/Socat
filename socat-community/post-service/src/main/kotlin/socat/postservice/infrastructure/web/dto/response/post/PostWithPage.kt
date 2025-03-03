package socat.postservice.infrastructure.web.dto.response.post

data class PostWithPage(
    val posts: List<PostResponse>,
    val total: Long,
    val pageNumber: Int,
    val totalPages: Int,
)
