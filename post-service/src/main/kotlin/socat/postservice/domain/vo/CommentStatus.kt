package socat.postservice.domain.vo

enum class CommentStatus(
    val status: String,
) {
    READ("R"), DELETE("D")
}