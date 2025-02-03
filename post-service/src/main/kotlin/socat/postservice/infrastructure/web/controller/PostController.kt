package socat.postservice.infrastructure.web.controller

import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.*
import org.springframework.web.bind.annotation.*
import socat.postservice.application.port.input.CreatePostUseCase
import socat.postservice.application.port.input.FindPostUseCase
import socat.postservice.application.port.input.ModifyPostUseCase
import socat.postservice.application.port.input.RemovePostUseCase
import socat.postservice.domain.model.Post
import socat.postservice.global.dto.APIResponse
import socat.postservice.global.utils.JwtProvider
import socat.postservice.infrastructure.web.dto.request.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.ModifyPostDTO
import socat.postservice.infrastructure.web.dto.request.RemovePostDTO
import socat.postservice.infrastructure.web.dto.response.PostResponse

@Slf4j
@RestController
class PostController(
        private val createPostUseCase: CreatePostUseCase,
        private val modifyPostUseCase: ModifyPostUseCase,
        private val removePostUseCase: RemovePostUseCase,
        private val findPostUseCase: FindPostUseCase,
        private val jwtProvider: JwtProvider
) : SwaggerPostController{

    @PostMapping("/posts")
    override fun createPost(
        @RequestBody createPostDTO: CreatePostDTO,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<APIResponse<PostResponse>> {
        val userId = jwtProvider.getUserIdWithBearer(token)

        val createPost = createPostUseCase.createPost(createPostDTO, userId)

        return status(HttpStatus.CREATED).body(APIResponse.ok(createPost.toDTO()))
    }


    @PutMapping("/posts")
    override fun modifyPost(modifyPostDTO: ModifyPostDTO): ResponseEntity<APIResponse<PostResponse>> {
        TODO("Not yet implemented")
    }

    @DeleteMapping("/posts")
    override fun removePost(removePostDTO: RemovePostDTO): ResponseEntity<APIResponse<Void>> {
        TODO("Not yet implemented")
    }

    @GetMapping("/posts/{postId}")
    override fun findPostById(
       @PathVariable("postId") postId: String
    ): ResponseEntity<APIResponse<PostResponse>> {

        val post: Post = findPostUseCase.findById(postId)

        return ok(APIResponse.ok(post.toDTO()))
    }

    @GetMapping(value = ["/posts"], produces = [MediaType.APPLICATION_JSON_VALUE])
    override fun findAllPost(): ResponseEntity<APIResponse<List<PostResponse>>> {
        val findAll = findPostUseCase.findAll()
        val toList: List<PostResponse> = findAll.map { it.toDTO() }.toList()

        return status(HttpStatus.OK).body(APIResponse.ok(toList))
    }

}