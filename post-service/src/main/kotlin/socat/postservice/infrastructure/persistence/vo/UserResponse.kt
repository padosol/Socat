package socat.postservice.infrastructure.persistence.vo

data class UserResponse(
    val username: String,
    val id: String,
    val email: String
)