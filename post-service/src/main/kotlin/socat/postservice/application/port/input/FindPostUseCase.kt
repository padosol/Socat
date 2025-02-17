package socat.postservice.application.port.input

import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.web.dto.response.PostWithPage

interface FindPostUseCase {
    fun findById(postId: String): Post
    fun findAll(): List<Post>
    fun findAllBySearch(page: Int, query: String): List<Post>
    fun findPostInRoomByRoomId(roomId: String): List<Post>
    fun findPostInRoomByRoomIdAndPageAndQuery(roomId: String, page: Int, query: String): PostWithPage
}