package socat.postservice.application.port.input.post

import socat.postservice.infrastructure.web.dto.request.RemovePostDTO

interface RemovePostUseCase {
    fun removePost(removePostDTO: RemovePostDTO, userId: String)
}