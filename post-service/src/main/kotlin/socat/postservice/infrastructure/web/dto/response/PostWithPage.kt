package socat.postservice.infrastructure.web.dto.response

import socat.postservice.domain.model.Post

data class PostWithPage(
    val posts: List<PostResponse>,
    val total: Int,
    val pageNumber: Int,
    val pageSize: Int,
)
