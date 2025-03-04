package socat.postservice.domain.service

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import socat.postservice.application.port.input.*
import socat.postservice.application.port.input.post.CreatePostUseCase
import socat.postservice.application.port.input.post.FindPostUseCase
import socat.postservice.application.port.input.post.ModifyPostUseCase
import socat.postservice.application.port.input.post.RemovePostUseCase
import socat.postservice.application.port.output.CategoryPersistencePort
import socat.postservice.application.port.output.PostPersistencePort
import socat.postservice.domain.model.Post
import socat.postservice.domain.service.exception.PostNotFoundException
import socat.postservice.domain.service.exception.CommunityNotFoundException
import socat.postservice.global.dto.APIResponse
import socat.postservice.global.exception.PostException
import socat.postservice.global.exception.PostExceptionCode
import socat.postservice.infrastructure.client.CommunityServiceClient
import socat.postservice.infrastructure.client.UserServiceClient
import socat.postservice.infrastructure.mapper.PostMapper
import socat.postservice.infrastructure.web.dto.request.post.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.post.ModifyPostDTO
import socat.postservice.infrastructure.web.dto.request.post.RemovePostDTO
import socat.postservice.infrastructure.vo.CommunityResponse
import socat.postservice.infrastructure.vo.UserResponse
import socat.postservice.infrastructure.web.dto.response.post.PostResponse
import socat.postservice.infrastructure.web.dto.response.post.PostWithPage
import java.time.LocalDateTime
import java.util.function.Supplier

@Service
class PostService(
    private val postPersistencePort: PostPersistencePort,
    private val userServiceClient: UserServiceClient,
    private val communityServiceClient: CommunityServiceClient,
    private val circuitBreakerRegistry: CircuitBreakerRegistry,
    private val categoryPersistencePort: CategoryPersistencePort,
) : CreatePostUseCase, ModifyPostUseCase, RemovePostUseCase, FindPostUseCase {

    private val log = LoggerFactory.getLogger(PostService::class.java)

    @Value("\${rootDir.path:Default 'D:\\'}")
    lateinit var rootDir: String

    override fun createPost(createPostDTO: CreatePostDTO, userId: String): Post {
        val userResponse = getUserResponse(userId)
        if (!userResponse.success) {
            throw PostException(PostExceptionCode.USER_NOT_FOUND)
        }

        val communityId = createPostDTO.communityId
        val communityResponse: APIResponse<CommunityResponse> = getCommunityResponse(communityId)
        if (!communityResponse.success) {
            throw CommunityNotFoundException(PostExceptionCode.COMMUNITY_NOT_FOUND)
        }

//        val category = categoryPersistencePort.findById(createPostDTO.communityId)

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

    override fun findById(postId: String): PostResponse {

        val post = (postPersistencePort.findById(postId)
            ?: throw PostNotFoundException(PostExceptionCode.POST_NOT_FOUND))

        val userResponse = getUserResponse(post.userId)

        return PostResponse(
            communityId = post.communityId,
            postId = post.postId,
            userId = post.userId,
            title = post.title,
            content = post.content,
            createdAt = post.createdAt,
            updatedAt = post.updatedAt,
            username = userResponse.data?.username,
        )
    }

    override fun findAll(): List<Post> {
        return postPersistencePort.findAll()
    }

    override fun findAllBySearch(page: Int, query: String): List<Post> {
        return postPersistencePort.findAllBySearch(page, query)
    }

    override fun findPostInRoomByRoomId(roomId: String): List<Post> {
        val communityResponse: APIResponse<CommunityResponse> = getCommunityResponse(roomId)

        if (!communityResponse.success) throw CommunityNotFoundException(PostExceptionCode.COMMUNITY_NOT_FOUND)

        return postPersistencePort.findPostInRoomByRoomId(roomId)
    }

    override fun findPostInRoomByRoomIdAndPageAndQuery(roomId: String, page: Int, query: String): PostWithPage {
        val communityResponse: APIResponse<CommunityResponse> = getCommunityResponse(roomId)

        if (!communityResponse.success) throw CommunityNotFoundException(PostExceptionCode.COMMUNITY_NOT_FOUND)

        val response =
            postPersistencePort.findPostInRoomByRoomIdAndPageAndQuery(roomId, page, query)

        val userIds = response.content.map { it.userId }.toList()

        var postResponse: List<PostResponse> = mutableListOf()
        val userResponseMulti = getUserResponseMulti(userIds)
        if (userResponseMulti.success) {
            val data = userResponseMulti.data!!
            val result: List<PostResponse> = response.content.map {
                data[it.userId]
                    ?.let { userResponse -> PostMapper.domainToDTO(it, userResponse.username) }
                    ?: PostMapper.domainToDTO(it, "익명")
            }
            postResponse = result
        }

        return PostWithPage(
            posts = postResponse,
            total = response.totalElements,
            pageNumber = response.number,
            totalPages = response.totalPages,
        )
    }

    fun getCommunityResponse(communityId: String): APIResponse<CommunityResponse> {
        val circuitBreaker = circuitBreakerRegistry.circuitBreaker("communityCircuitBreaker")
        val supplier: Supplier<APIResponse<CommunityResponse>> = Supplier { communityServiceClient.getCommunity(communityId) }
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