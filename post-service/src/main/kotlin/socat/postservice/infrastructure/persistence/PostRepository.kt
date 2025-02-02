package socat.postservice.infrastructure.persistence

import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Repository
import socat.postservice.application.port.output.PostPersistencePort
import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.persistence.entity.PostEntity

@Slf4j
@Repository
class PostRepository(
        private val jpaPostRepository: JpaPostRepository
) : PostPersistencePort{
        override fun createPost(post: Post): Post {
                val postEntity = post.toEntity()

                val save = jpaPostRepository.save(postEntity)

                return save.toDomain()
        }

        override fun modifyPost(post: Post): Post {
                TODO("Not yet implemented")
        }

        override fun removePost(post: Post): Post {
                TODO("Not yet implemented")
        }

        override fun findById(postId: String): Post {
                TODO("Not yet implemented")
        }

        override fun findAll(): List<Post> {
                val findAll: List<PostEntity> = jpaPostRepository.findAll()

                return findAll.map { it.toDomain() }.toList()
        }
}