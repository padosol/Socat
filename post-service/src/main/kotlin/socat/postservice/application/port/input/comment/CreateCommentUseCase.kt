package socat.postservice.application.port.input.comment

import socat.postservice.domain.model.Comment
import socat.postservice.infrastructure.web.dto.request.comment.CreateCommentDTO

interface CreateCommentUseCase {
    fun create(createCommentDTO: CreateCommentDTO, userId: String): Comment
}