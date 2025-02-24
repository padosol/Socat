package socat.postservice.application.port.input.post

import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.web.dto.request.post.CreatePostDTO

interface CreatePostUseCase {
    fun createPost(createPostDTO: CreatePostDTO, userId: String): Post
}