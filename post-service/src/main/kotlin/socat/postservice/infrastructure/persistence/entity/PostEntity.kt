package socat.postservice.infrastructure.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class PostEntity(
        @Id
        private val id: Long
) {
}