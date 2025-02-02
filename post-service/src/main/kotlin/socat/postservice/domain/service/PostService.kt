package socat.postservice.domain.service

import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import socat.postservice.application.port.input.CreatePostUseCase
import socat.postservice.application.port.input.FindPostUseCase
import socat.postservice.application.port.input.ModifyPostUseCase
import socat.postservice.application.port.input.RemovePostUseCase
import socat.postservice.application.port.output.PostPersistencePort
import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.persistence.client.UserServiceClient
import socat.postservice.infrastructure.persistence.vo.UserResponse
import socat.postservice.infrastructure.web.dto.request.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.ModifyPostDTO
import socat.postservice.infrastructure.web.dto.request.RemovePostDTO

@Slf4j
@Service
class PostService(
    private val postPersistencePort: PostPersistencePort,
    private val userServiceClient: UserServiceClient
) : CreatePostUseCase, ModifyPostUseCase, RemovePostUseCase, FindPostUseCase{
    override fun createPost(createPostDTO: CreatePostDTO, userId: String): Post {
        val post = Post.createPost(createPostDTO)
        return postPersistencePort.createPost(post)
    }

    override fun modifyPost(modifyPostDTO: ModifyPostDTO): Post {
        TODO("Not yet implemented")
    }

    override fun removePost(removePostDTO: RemovePostDTO): Post {
        TODO("Not yet implemented")
    }

    override fun findById(postId: String): Post {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Post> {
        return  postPersistencePort.findAll()
    }
}