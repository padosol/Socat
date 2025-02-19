package socat.postservice.infrastructure.persistence

import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import socat.postservice.application.port.output.PostPersistencePort
import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.persistence.entity.PostEntity
import socat.postservice.infrastructure.web.dto.response.PostWithPage

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
                return jpaPostRepository.findByIdOrNull(postId)
                        ?.toDomain()
        }

        override fun findAll(): List<Post> {
                val findAll: List<PostEntity> = jpaPostRepository.findAll()

                return findAll.map { it.toDomain() }.toList()
        }

        override fun findPostInRoomByRoomId(roomId: String): List<Post> {
                val posts: List<PostEntity> = jpaPostRepository.findPostEntitiesByRoomId(roomId)

                return posts.map { it.toDomain() }.toList()
        }

        override fun findPostInRoomByRoomIdAndPageAndQuery(roomId: String, page: Int, query: String): Page<Post> {
                val pageable: Pageable = PageRequest.of(page - 1, 10)
                val result: Page<PostEntity> = jpaPostRepository.findAllByRoomIdOrderByCreatedAtDesc(roomId, pageable)
                return result.map { it.toDomain() }
        }

        override fun findAllBySearch(page: Int, query: String): List<Post> {
                val pageable: Pageable = PageRequest.of(page, 10)

                val result: Page<PostEntity> = jpaPostRepository.findAllByOrderByCreatedAtDesc(pageable)

                return result.toList().map { it.toDomain() }
        }

        override fun totalCount(query: String): Int {
                TODO("Not yet implemented")
        }
}