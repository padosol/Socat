package socat.postservice.application.port.input

import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.web.dto.request.RemovePostDTO

interface RemovePostUseCase {
    fun removePost(removePostDTO: RemovePostDTO): Post
}