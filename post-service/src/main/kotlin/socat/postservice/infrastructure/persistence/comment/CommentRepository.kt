package socat.postservice.infrastructure.persistence.comment

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import socat.postservice.application.port.output.CommentPersistencePort
import socat.postservice.domain.model.Comment
import socat.postservice.domain.vo.CommentStatus
import socat.postservice.infrastructure.mapper.CommentMapper
import socat.postservice.infrastructure.persistence.comment.entity.QCommentEntity.*
import socat.postservice.infrastructure.persistence.user.UserRepository


@Repository
class CommentRepository(
    private val jpaCommentRepository: JpaCommentRepository,
    private val queryFactory: JPAQueryFactory,
    private val userRepository: UserRepository
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
        return queryFactory
            .selectFrom(commentEntity)
            .leftJoin(commentEntity.parent)
            .fetchJoin()
            .where(
                commentEntity.status.eq(CommentStatus.READ)
                    .and(commentEntity.parent.commentId.isNull)
                    .and(commentEntity.postId.eq(postId))
            )
            .orderBy(commentEntity.createdAt.asc())
            .fetch().map { CommentMapper.entityToDomain(it) }
    }
}