package socat.postservice.application.port.output

import socat.postservice.domain.model.Post

interface PostPersistencePort {

    fun createPost(post: Post): Post

    fun modifyPost(post: Post): Post

    fun removePost(post: Post): Post

    fun findById(postId: String): Post

    fun findAll(): List<Post>
}