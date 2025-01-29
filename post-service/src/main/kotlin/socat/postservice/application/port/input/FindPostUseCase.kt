package socat.postservice.application.port.input

import socat.postservice.domain.model.Post

interface FindPostUseCase {
    fun findById(): Post
    fun findAll(): List<Post>
}