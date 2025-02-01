package socat.postservice.application.port.input

import socat.postservice.domain.model.Post

interface FindPostUseCase {
    fun findById(postId: String): Post
    fun findAll(): List<Post>
}