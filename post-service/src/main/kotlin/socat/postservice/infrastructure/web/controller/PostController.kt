package socat.postservice.infrastructure.web.controller

import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import socat.postservice.application.port.input.post.CreatePostUseCase
import socat.postservice.application.port.input.post.FindPostUseCase
import socat.postservice.application.port.input.post.ModifyPostUseCase
import socat.postservice.application.port.input.post.RemovePostUseCase
import socat.postservice.domain.model.Post
import socat.postservice.global.dto.APIResponse
import socat.postservice.global.utils.JwtProvider
import socat.postservice.infrastructure.s3.S3FileManagement
import socat.postservice.infrastructure.web.dto.request.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.ModifyPostDTO
import socat.postservice.infrastructure.web.dto.request.RemovePostDTO
import socat.postservice.infrastructure.web.dto.response.PostResponse
import socat.postservice.infrastructure.web.dto.response.PostWithPage

@Slf4j
@RestController
class PostController(
    private val createPostUseCase: CreatePostUseCase,
    private val modifyPostUseCase: ModifyPostUseCase,
    private val removePostUseCase: RemovePostUseCase,
    private val findPostUseCase: FindPostUseCase,
    private val jwtProvider: JwtProvider,
    private val s3FileManagement: S3FileManagement
) : SwaggerPostController{

    /**
     * 게시글 등록
     */
    @PostMapping("/posts")
    override fun createPost(
        @RequestBody createPostDTO: CreatePostDTO,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<APIResponse<PostResponse>> {
        val userId = jwtProvider.getUserIdWithBearer(token)

        val createPost = createPostUseCase.createPost(createPostDTO, userId)

        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.ok(createPost.toDTO()))
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/posts")
    override fun modifyPost(
        @RequestBody modifyPostDTO: ModifyPostDTO,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<APIResponse<PostResponse>> {
        val userId = jwtProvider.getUserIdWithBearer(token)

        val modifyPost = modifyPostUseCase.modifyPost(modifyPostDTO, userId)

        return ResponseEntity.ok(APIResponse.ok(modifyPost.toDTO()))
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/posts")
    override fun removePost(
        @RequestBody removePostDTO: RemovePostDTO,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<APIResponse<Nothing?>> {
        val userId = jwtProvider.getUserIdWithBearer(token)

        removePostUseCase.removePost(removePostDTO, userId)

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(APIResponse.ok(null))
    }

    /**
     * 게시글 ID 로 게시글 조회
     */
    @GetMapping("/posts/{postId}")
    override fun findPostById(
       @PathVariable("postId") postId: String
    ): ResponseEntity<APIResponse<PostResponse>> {

        val post: Post = findPostUseCase.findById(postId)

        return ResponseEntity.ok(APIResponse.ok(post.toDTO()))
    }

    /**
     * 게시글 전체 조회
     */
    @GetMapping(value = ["/posts"])
    override fun findAllPost(
        @RequestParam(value = "page", defaultValue = "0", required = false) page: Int,
        @RequestParam(value = "query", required = false) query: String,
    ): ResponseEntity<APIResponse<List<PostResponse>>> {
        val findAll = findPostUseCase.findAllBySearch(page, query)
        val toList: List<PostResponse> = findAll.map { it.toDTO() }.toList()

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.ok(toList))
    }

    /**
     * 소켓에 등록된 게시글 조회
     */
    @GetMapping("/{roomId}/posts")
    override fun findPostInRoomByRoomId(
        @PathVariable("roomId") roomId: String,
        @RequestParam("page") page: Int,
        @RequestParam("query") query: String,
    ): ResponseEntity<APIResponse<PostWithPage>> {

        val posts: PostWithPage = findPostUseCase.findPostInRoomByRoomIdAndPageAndQuery(roomId, page, query)

        return ResponseEntity.ok(APIResponse.ok(posts))
    }

    @PostMapping("/upload")
    override fun fileUpload(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<APIResponse<String>> {

        val fileURL = s3FileManagement.uploadFile(file)

        return ResponseEntity.ok(APIResponse.ok(fileURL))
    }

}