package socat.postservice.application.port.input.post

import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.web.dto.request.ModifyPostDTO

interface ModifyPostUseCase {
    fun modifyPost(modifyPostDTO: ModifyPostDTO, userId: String): Post
}