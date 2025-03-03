package socat.postservice.infrastructure.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import socat.postservice.global.dto.APIResponse
import socat.postservice.infrastructure.web.dto.request.category.CreateCategoryDTO
import socat.postservice.infrastructure.web.dto.request.category.ModifyCategoryDTO
import socat.postservice.infrastructure.web.dto.request.category.RemoveCategoryDTO
import socat.postservice.infrastructure.web.dto.response.category.CategoryResponse

@Tag(name = "category", description = "게시글 카테고리 API")
interface SwaggerCategory {

    @Operation(summary = "카테고리 등록", description = "")
    fun create(
        createCategoryDTO: CreateCategoryDTO,
        token: String,
    ) : ResponseEntity<APIResponse<CategoryResponse>>

    // 수정
    fun modify(
        modifyCategoryDTO: ModifyCategoryDTO,
        token: String,
    ) : ResponseEntity<APIResponse<CategoryResponse>>

    // 삭제
    fun remove(
        removeCategoryDTO: RemoveCategoryDTO,
        token: String,
    ) : ResponseEntity<APIResponse<Any>>

    // 단건 조회
    fun findById(
        categoryId: String
    ) : ResponseEntity<APIResponse<CategoryResponse>>

    // 전체 조회
    fun findAll() : ResponseEntity<APIResponse<List<CategoryResponse>>>


}