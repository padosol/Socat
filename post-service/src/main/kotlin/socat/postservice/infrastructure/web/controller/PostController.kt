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
import socat.postservice.infrastructure.mapper.PostMapper
import socat.postservice.infrastructure.s3.S3FileManagement
import socat.postservice.infrastructure.web.dto.request.post.CreatePostDTO
import socat.postservice.infrastructure.web.dto.request.post.ModifyPostDTO
import socat.postservice.infrastructure.web.dto.request.post.RemovePostDTO
import socat.postservice.infrastructure.web.dto.response.post.PostResponse
import socat.postservice.infrastructure.web.dto.response.post.PostWithPage

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

        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.ok(PostMapper.domainToDTO(createPost)))
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

        return ResponseEntity.ok(APIResponse.ok(PostMapper.domainToDTO(modifyPost)))
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

        val post: PostResponse = findPostUseCase.findById(postId)

        return ResponseEntity.ok(APIResponse.ok(post))
    }

    /**
     * 게시글 전체 조회
     */
    @GetMapping(value = ["/posts"])
    override fun findAllPost(
        @RequestParam(value = "page", defaultValue = "1", required = false) page: Int,
        @RequestParam(value = "query", required = false) query: String,
    ): ResponseEntity<APIResponse<List<PostResponse>>> {
        val findAll = findPostUseCase.findAllBySearch(page, query)
        val toList: List<PostResponse> = findAll.map { PostMapper.domainToDTO(it) }.toList()

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.ok(toList))
    }

    /**
     * 커뮤니티에 등록된 게시글 조회
     */
    @GetMapping("/{communityId}/posts")
    override fun findPostInCommunityByRoomId(
        @PathVariable("communityId") communityId: String,
        @RequestParam(value = "page", defaultValue = "1", required = false) page: Int,
        @RequestParam("query", defaultValue = "", required = false) query: String,
    ): ResponseEntity<APIResponse<PostWithPage>> {

        val posts: PostWithPage = findPostUseCase.findPostInRoomByRoomIdAndPageAndQuery(communityId, page, query)

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