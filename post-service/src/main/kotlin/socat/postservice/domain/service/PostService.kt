package socat.postservice.domain.service

import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import socat.postservice.application.port.input.CreatePostUseCase
import socat.postservice.application.port.input.ModifyPostUseCase
import socat.postservice.application.port.input.RemovePostUseCase
import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.web.dto.request.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.ModifyPostDTO
import socat.postservice.infrastructure.web.dto.request.RemovePostDTO

@Slf4j
@Service
class PostService : CreatePostUseCase, ModifyPostUseCase, RemovePostUseCase{
    override fun createPost(postDTO: CreatePostDTO): Post {
        TODO("Not yet implemented")
    }

    override fun modifyPost(modifyPostDTO: ModifyPostDTO): Post {
        TODO("Not yet implemented")
    }

    override fun removePost(removePostDTO: RemovePostDTO): Post {
        TODO("Not yet implemented")
    }
}