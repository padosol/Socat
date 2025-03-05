package socat.postservice.infrastructure.persistence.comment

import org.springframework.data.jpa.repository.JpaRepository
import socat.postservice.infrastructure.persistence.comment.entity.CommentEntity

interface JpaCommentRepository : JpaRepository<CommentEntity, String>{

    fun findAllByPostId(postId: String): List<CommentEntity>

}