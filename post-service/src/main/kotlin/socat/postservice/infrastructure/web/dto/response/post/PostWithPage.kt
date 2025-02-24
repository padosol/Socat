package socat.postservice.infrastructure.web.dto.response.post

data class PostWithPage(
    val posts: List<PostResponse>,
    val total: Int,
    val pageNumber: Int,
    val pageSize: Int,
)
