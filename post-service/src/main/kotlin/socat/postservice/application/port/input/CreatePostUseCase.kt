package socat.postservice.application.port.input

import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.web.dto.request.CreatePostDTO

interface CreatePostUseCase {
    fun createPost(createPostDTO: CreatePostDTO, userId: String): Post
}