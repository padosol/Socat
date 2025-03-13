package socat.postservice.infrastructure.persistence.user.entity

import jakarta.persistence.*

@Entity
@Table(name = "post_users")
class UserEntity(
    @Id @Column(name = "user_id", length = 36)
    val id: String,

    @Column(name = "user_name", length = 10)
    var userName: String? = null,
)