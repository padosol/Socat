package socat.postservice.domain.service

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import socat.postservice.application.port.input.*
import socat.postservice.application.port.output.PostPersistencePort
import socat.postservice.domain.model.Post
import socat.postservice.domain.service.exception.PostNotFoundException
import socat.postservice.domain.service.exception.RoomNotFoundException
import socat.postservice.global.dto.APIResponse
import socat.postservice.global.exception.PostException
import socat.postservice.global.exception.PostExceptionCode
import socat.postservice.infrastructure.client.RoomServiceClient
import socat.postservice.infrastructure.client.UserServiceClient
import socat.postservice.infrastructure.web.dto.request.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.ModifyPostDTO
import socat.postservice.infrastructure.web.dto.request.RemovePostDTO
import socat.postservice.infrastructure.vo.RoomResponse
import socat.postservice.infrastructure.vo.UserResponse
import socat.postservice.infrastructure.web.dto.response.PostResponse
import socat.postservice.infrastructure.web.dto.response.PostWithPage
import java.util.function.Supplier

@Service
class PostService(
    private val postPersistencePort: PostPersistencePort,
    private val userServiceClient: UserServiceClient,
    private val roomServiceClient: RoomServiceClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry
) : CreatePostUseCase, ModifyPostUseCase, RemovePostUseCase, FindPostUseCase{

    private val log = LoggerFactory.getLogger(PostService::class.java)

    @Value("\${rootDir.path:Default 'D:\\'}")
    lateinit var rootDir: String

    override fun createPost(createPostDTO: CreatePostDTO, userId: String): Post {
        val userResponse = getUserResponse(userId)
        if (!userResponse.success) {
            throw PostException(PostExceptionCode.USER_NOT_FOUND)
        }

        val roomId = createPostDTO.roomId
        val roomResponse: APIResponse<RoomResponse> = getRoomResponse(roomId)
        if (!roomResponse.success) {
            throw RoomNotFoundException(PostExceptionCode.ROOM_NOT_FOUND)
        }

        val post = Post.createPost(createPostDTO, userId)
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
        return postPersistencePort.findAll()
    }

    override fun findAllBySearch(page: Int, query: String): List<Post> {
        return postPersistencePort.findAllBySearch(page, query)
    }

    override fun findPostInRoomByRoomId(roomId: String): List<Post> {
        val roomResponse: APIResponse<RoomResponse> = getRoomResponse(roomId)

        if (!roomResponse.success) throw RoomNotFoundException(PostExceptionCode.ROOM_NOT_FOUND)

        return postPersistencePort.findPostInRoomByRoomId(roomId)
    }

    override fun findPostInRoomByRoomIdAndPageAndQuery(roomId: String, page: Int, query: String): PostWithPage {
        val roomResponse: APIResponse<RoomResponse> = getRoomResponse(roomId)

        if (!roomResponse.success) throw RoomNotFoundException(PostExceptionCode.ROOM_NOT_FOUND)

        val response =
            postPersistencePort.findPostInRoomByRoomIdAndPageAndQuery(roomId, page, query)

        val userIds = response.content.map { it.userId }.toList()

        var postResponse: List<PostResponse> = mutableListOf()
        val userResponseMulti = getUserResponseMulti(userIds)
        if (userResponseMulti.success) {
            val data = userResponseMulti.data!!
            val result: List<PostResponse> = response.content.map {
                data[it.userId]
                    ?.let { userResponse -> it.toDTO(userResponse.username) }
                    ?: it.toDTO("익명")
            }
            postResponse = result
        }

        return PostWithPage(
            posts = postResponse,
            total = response.numberOfElements,
            pageNumber = response.number,
            pageSize = response.size,
        )
    }

    fun getRoomResponse(roomId: String): APIResponse<RoomResponse> {
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("roomCircuitBreaker")
        val supplier: Supplier<APIResponse<RoomResponse>> = Supplier { roomServiceClient.getRoom(roomId) }
        val decoratedSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, supplier)

        return runCatching { decoratedSupplier.get() }
            .recover { APIResponse.fail(null) }
            .getOrThrow()
    }
    fun getUserResponse(userId: String): APIResponse<UserResponse> {
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("userCircuitBreaker")
        val supplier = Supplier { userServiceClient.getUser(userId) }
        val decoratedSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, supplier)

        return runCatching { decoratedSupplier.get() }
            .recover { APIResponse.fail(null) }
            .getOrThrow()
    }

    fun getUserResponseMulti(userIds: List<String>): APIResponse<Map<String, UserResponse>> {
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("userCircuitBreaker")
        val supplier = Supplier { userServiceClient.getUserMulti(userIds) }
        val decoratedSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, supplier)

        return runCatching { decoratedSupplier.get() }
            .recover { APIResponse.fail(null) }
            .getOrThrow()
    }

}