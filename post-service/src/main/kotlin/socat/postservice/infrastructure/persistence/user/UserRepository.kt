package socat.postservice.infrastructure.persistence.user

import org.springframework.data.jpa.repository.JpaRepository
import socat.postservice.infrastructure.persistence.user.entity.UserEntity

interface UserRepository : JpaRepository<UserEntity, String> {
}