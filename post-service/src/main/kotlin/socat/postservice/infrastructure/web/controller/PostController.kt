package socat.postservice.infrastructure.web.controller

import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import socat.postservice.application.port.input.CreatePostUseCase
import socat.postservice.application.port.input.FindPostUseCase
import socat.postservice.application.port.input.ModifyPostUseCase
import socat.postservice.application.port.input.RemovePostUseCase
import socat.postservice.domain.model.Post
import socat.postservice.global.dto.APIResponse
import socat.postservice.infrastructure.web.dto.request.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.ModifyPostDTO
import socat.postservice.infrastructure.web.dto.request.RemovePostDTO
import socat.postservice.infrastructure.web.dto.request.SearchPostDTO
import socat.postservice.infrastructure.web.dto.response.PostResponse

@Slf4j
@RestController
class PostController(
        private val createPostUseCase: CreatePostUseCase,
        private val modifyPostUseCase: ModifyPostUseCase,
        private val removePostUseCase: RemovePostUseCase,
        private val findPostUseCase: FindPostUseCase
) : SwaggerPostController{
    override fun createPost(
            createPostDTO: CreatePostDTO,
            @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<APIResponse<PostResponse>> {
        TODO("Not yet implemented")

    }


    override fun modifyPost(modifyPostDTO: ModifyPostDTO): ResponseEntity<APIResponse<PostResponse>> {
        TODO("Not yet implemented")
    }

    override fun removePost(removePostDTO: RemovePostDTO): ResponseEntity<APIResponse<Void>> {
        TODO("Not yet implemented")
    }

    override fun findPostById(postId: String): ResponseEntity<APIResponse<PostResponse>> {

        val post: Post = findPostUseCase.findById(postId)

        return ResponseEntity.ok(APIResponse.ok(post.toDTO()))
    }

    override fun findAllPost(searchPostDTO: SearchPostDTO): ResponseEntity<APIResponse<List<PostResponse>>> {

        TODO("Not yet implemented")
    }


}