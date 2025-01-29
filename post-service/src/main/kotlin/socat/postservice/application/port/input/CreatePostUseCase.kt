package socat.postservice.application.port.input

import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.web.dto.request.CreatePostDTO

interface CreatePostUseCase {
    fun createPost(postDTO: CreatePostDTO): Post
}