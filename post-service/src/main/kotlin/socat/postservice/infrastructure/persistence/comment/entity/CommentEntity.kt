package socat.postservice.infrastructure.persistence.comment.entity

import jakarta.persistence.*
import socat.postservice.domain.vo.CommentStatus
import java.time.LocalDateTime

@Entity
@Table(name = "comments")
class CommentEntity(
    @Id
    val commentId: String,

    val postId: String,
    val userId: String,
    val comment: String,

    val likes: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    val parent: CommentEntity? = null,

    @OneToMany(mappedBy = "parent")
    val children: List<CommentEntity> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    val status: CommentStatus,

    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null,
) {

}