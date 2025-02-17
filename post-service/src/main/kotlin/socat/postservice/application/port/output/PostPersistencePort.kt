package socat.postservice.application.port.output

import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.web.dto.response.PostWithPage

interface PostPersistencePort {

    fun savePost(post: Post): Post

    fun modifyPost(post: Post): Post

    fun removePost(post: Post)

    fun findById(postId: String): Post?

    fun findAll(): List<Post>

    fun findPostInRoomByRoomId(roomId: String): List<Post>

    fun findPostInRoomByRoomIdAndPageAndQuery(roomId: String, page: Int, query: String): PostWithPage

    fun findAllBySearch(page: Int, query: String): List<Post>

    fun totalCount(query: String): Int
}