package socat.postservice.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import socat.postservice.infrastructure.persistence.entity.PostEntity

interface JpaPostRepository :JpaRepository<PostEntity, Long> {
}