package socat.postservice.application.port.input.post

import socat.postservice.infrastructure.web.dto.request.post.RemovePostDTO

interface RemovePostUseCase {
    fun removePost(removePostDTO: RemovePostDTO, userId: String)
}