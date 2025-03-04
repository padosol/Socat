package socat.postservice.application.port.input.comment

interface RemoveCommentUseCase {
    fun remove(commentId: String, userId: String)
}