package socat.postservice.infrastructure.persistence.comment

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import socat.postservice.application.port.output.CommentPersistencePort
import socat.postservice.domain.model.Comment
import socat.postservice.infrastructure.mapper.CommentMapper
import socat.postservice.infrastructure.mapper.PostMapper

@Repository
class CommentRepository(
    private val jpaCommentRepository: JpaCommentRepository
) : CommentPersistencePort {
    override fun save(comment: Comment): Comment {
        val commentEntity = jpaCommentRepository.save(CommentMapper.domainToEntity(comment))
        return CommentMapper.entityToDomain(commentEntity)
    }

    override fun remove(commentId: String) {
        TODO("Not yet implemented")
    }

    override fun findByCommentId(commentId: String): Comment? {
        return jpaCommentRepository.findByIdOrNull(commentId)
            ?.let { CommentMapper.entityToDomain(it) }
    }

    override fun findAllByPostId(postId: String): List<Comment> {
        return jpaCommentRepository.findAllByPostId(postId).map { CommentMapper.entityToDomain(it) }
    }
}