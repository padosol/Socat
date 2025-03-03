package socat.postservice.application.port.input.post

import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.web.dto.response.post.PostResponse
import socat.postservice.infrastructure.web.dto.response.post.PostWithPage

interface FindPostUseCase {
    fun findById(postId: String): PostResponse
    fun findAll(): List<Post>
    fun findAllBySearch(page: Int, query: String): List<Post>
    fun findPostInRoomByRoomId(roomId: String): List<Post>
    fun findPostInRoomByRoomIdAndPageAndQuery(roomId: String, page: Int, query: String): PostWithPage
}