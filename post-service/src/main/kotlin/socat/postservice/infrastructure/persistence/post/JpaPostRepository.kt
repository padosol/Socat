package socat.postservice.infrastructure.persistence.post

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import socat.postservice.infrastructure.persistence.post.entity.PostEntity

interface JpaPostRepository : JpaRepository<PostEntity, String> {

    fun findPostEntitiesByCommunityId(roomId: String): List<PostEntity>

    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): Page<PostEntity>

    fun findAllByCommunityIdOrderByCreatedAtDesc(roomId: String, pageable: Pageable): Page<PostEntity>

}