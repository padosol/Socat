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
        override fun savePost(post: Post): Post {
                val postEntity = post.toEntity()

                val save = jpaPostRepository.save(postEntity)

                return save.toDomain()
        }

        override fun modifyPost(post: Post): Post {
                val postEntity = post.toEntity()

                val save = jpaPostRepository.save(postEntity)

                return save.toDomain()
        }

        override fun removePost(post: Post) {
                val postEntity = post.toEntity()

                jpaPostRepository.delete(postEntity)
        }

        override fun findById(postId: String): Post? {
                TODO("Not yet implemented")
        }

        override fun findAll(): List<Post> {
                val findAll: List<PostEntity> = jpaPostRepository.findAll()

                return findAll.map { it.toDomain() }.toList()
        }

        override fun findPostInRoomByRoomId(roomId: String): List<Post> {
                val posts: List<PostEntity> = jpaPostRepository.findPostEntitiesByRoomId(roomId)

                return posts.map { it.toDomain() }.toList()
        }
}