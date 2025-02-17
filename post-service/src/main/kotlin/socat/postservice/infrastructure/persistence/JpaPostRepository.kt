package socat.postservice.infrastructure.persistence

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import socat.postservice.infrastructure.persistence.entity.PostEntity

interface JpaPostRepository :JpaRepository<PostEntity, Long> {

    fun findPostEntitiesByRoomId(roomId: String): List<PostEntity>

    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<PostEntity>

    fun findAllByRoomIdOrderByCreatedAtDesc(roomId: String, pageable: Pageable): Page<PostEntity>

}