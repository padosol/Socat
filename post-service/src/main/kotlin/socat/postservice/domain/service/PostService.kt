package socat.postservice.domain.service

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import socat.postservice.application.port.input.CreatePostUseCase
import socat.postservice.application.port.input.FindPostUseCase
import socat.postservice.application.port.input.ModifyPostUseCase
import socat.postservice.application.port.input.RemovePostUseCase
import socat.postservice.application.port.output.PostPersistencePort
import socat.postservice.domain.model.Post
import socat.postservice.domain.service.exception.PostNotFoundException
import socat.postservice.domain.service.exception.RoomNotFoundException
import socat.postservice.global.dto.APIResponse
import socat.postservice.global.exception.PostExceptionCode
import socat.postservice.infrastructure.client.RoomServiceClient
import socat.postservice.infrastructure.client.UserServiceClient
import socat.postservice.infrastructure.web.dto.request.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.ModifyPostDTO
import socat.postservice.infrastructure.web.dto.request.RemovePostDTO
import socat.postservice.infrastructure.vo.RoomResponse
import java.util.function.Supplier

@Service
class PostService(
    private val postPersistencePort: PostPersistencePort,
    private val userServiceClient: UserServiceClient,
    private val roomServiceClient: RoomServiceClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) : CreatePostUseCase, ModifyPostUseCase, RemovePostUseCase, FindPostUseCase{

    private val log = LoggerFactory.getLogger(PostService::class.java)

    override fun createPost(createPostDTO: CreatePostDTO, userId: String): Post {
        val roomId = createPostDTO.roomId

        val roomResponse: APIResponse<RoomResponse> = getRoomResponse(roomId)

        if (!roomResponse.success) {
            throw RoomNotFoundException(PostExceptionCode.ROOM_NOT_FOUND)
        }

        val post = Post.createPost(createPostDTO)
        return postPersistencePort.savePost(post)
    }

    override fun modifyPost(modifyPostDTO: ModifyPostDTO, userId: String): Post {
        val findPost: Post = postPersistencePort.findById(modifyPostDTO.postId)
            ?: throw PostNotFoundException(PostExceptionCode.POST_NOT_FOUND)

        findPost.modify(modifyPostDTO)

        return postPersistencePort.savePost(findPost)
    }

    override fun removePost(removePostDTO: RemovePostDTO, userId: String) {
        val findPost: Post = postPersistencePort.findById(removePostDTO.postId)
            ?: throw PostNotFoundException(PostExceptionCode.POST_NOT_FOUND)

        postPersistencePort.removePost(findPost)
    }

    override fun findById(postId: String): Post {
        return postPersistencePort.findById(postId)
            ?: throw PostNotFoundException(PostExceptionCode.POST_NOT_FOUND)
    }

    override fun findAll(): List<Post> {
        return  postPersistencePort.findAll()
    }

    override fun findPostInRoomByRoomId(roomId: String): List<Post> {
        val roomResponse: APIResponse<RoomResponse> = getRoomResponse(roomId)

        if (!roomResponse.success) throw RoomNotFoundException(PostExceptionCode.ROOM_NOT_FOUND)

        return postPersistencePort.findPostInRoomByRoomId(roomId)
    }

    fun getRoomResponse(roomId: String): APIResponse<RoomResponse> {
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("roomCircuitBreaker")
        val supplier: Supplier<APIResponse<RoomResponse>> = Supplier { roomServiceClient.getRoom(roomId) }
        val decoratedSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, supplier)

        return runCatching { decoratedSupplier.get() }
                .recover { APIResponse.fail(null) }
                .getOrThrow()
    }
}