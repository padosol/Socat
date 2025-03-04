package socat.postservice.application.port.input.comment

import socat.postservice.infrastructure.web.dto.request.comment.ModifyCommentDTO
import socat.postservice.domain.model.Comment

interface ModifyCommentUseCase {
    fun modify(modifyCommentDTO: ModifyCommentDTO, userId: String): Comment
}