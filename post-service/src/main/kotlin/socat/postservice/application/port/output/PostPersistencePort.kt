package socat.postservice.application.port.output

import org.springframework.data.domain.Page
import socat.postservice.domain.model.Post

interface PostPersistencePort {

    fun savePost(post: Post): Post

    fun modifyPost(post: Post): Post

    fun removePost(post: Post)

    fun findById(postId: String): Post?

    fun findAll(): List<Post>

    fun findPostInRoomByRoomId(communityId: String): List<Post>

    fun findPostInRoomByRoomIdAndPageAndQuery(communityId: String, page: Int, query: String): Page<Post>

    fun findAllBySearch(page: Int, query: String): List<Post>

    fun totalCount(query: String): Int
}