package socat.postservice.application.port.input

import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.web.dto.request.ModifyPostDTO

interface ModifyPostUseCase {
    fun modifyPost(modifyPostDTO: ModifyPostDTO): Post
}