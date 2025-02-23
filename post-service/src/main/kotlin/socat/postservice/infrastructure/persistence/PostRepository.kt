package socat.postservice.infrastructure.persistence

import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import socat.postservice.application.port.output.PostPersistencePort
import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.mapper.PostMapper
import socat.postservice.infrastructure.persistence.entity.PostEntity
import socat.postservice.infrastructure.web.dto.response.PostWithPage

@Slf4j
@Repository
class PostRepository(
        private val jpaPostRepository: JpaPostRepository
) : PostPersistencePort{
        override fun savePost(post: Post): Post {
                val postEntity = PostMapper.domainToEntity(post)

                val save = jpaPostRepository.save(postEntity)

                return PostMapper.entityToDomain(save)
        }

        override fun modifyPost(post: Post): Post {
                val postEntity = PostMapper.domainToEntity(post)

                val save = jpaPostRepository.save(postEntity)

                return PostMapper.entityToDomain(save)
        }

        override fun removePost(post: Post) {
                val postEntity = PostMapper.domainToEntity(post)

                jpaPostRepository.delete(postEntity)
        }

        override fun findById(postId: String): Post? {
                return jpaPostRepository.findByIdOrNull(postId)
                        ?.let { PostMapper.entityToDomain(it) }
        }

        override fun findAll(): List<Post> {
                val findAll: List<PostEntity> = jpaPostRepository.findAll()

                return findAll.map { PostMapper.entityToDomain(it) }.toList()
        }

        override fun findPostInRoomByRoomId(roomId: String): List<Post> {
                val posts: List<PostEntity> = jpaPostRepository.findPostEntitiesByRoomId(roomId)

                return posts.map { PostMapper.entityToDomain(it) }.toList()
        }

        override fun findPostInRoomByRoomIdAndPageAndQuery(roomId: String, page: Int, query: String): Page<Post> {
                val pageable: Pageable = PageRequest.of(page - 1, 10)
                val result: Page<PostEntity> = jpaPostRepository.findAllByRoomIdOrderByCreatedAtDesc(roomId, pageable)
                return result.map { PostMapper.entityToDomain(it) }
        }

        override fun findAllBySearch(page: Int, query: String): List<Post> {
                val pageable: Pageable = PageRequest.of(page, 10)

                val result: Page<PostEntity> = jpaPostRepository.findAllByOrderByCreatedAtDesc(pageable)

                return result.toList().map { PostMapper.entityToDomain(it) }
        }

        override fun totalCount(query: String): Int {
                TODO("Not yet implemented")
        }
}