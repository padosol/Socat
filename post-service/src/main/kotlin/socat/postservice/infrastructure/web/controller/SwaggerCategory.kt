package socat.postservice.infrastructure.web.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import socat.postservice.global.dto.APIResponse
import socat.postservice.infrastructure.web.dto.request.category.CreateCategoryDTO
import socat.postservice.infrastructure.web.dto.response.category.CategoryResponse

@Tag(name = "category", description = "게시글 카테고리 API")
interface SwaggerCategory {

    @Operation(summary = "카테고리 등록", description = "")
    fun create(
        @RequestBody createCategoryDTO: CreateCategoryDTO
    ): ResponseEntity<APIResponse<CategoryResponse>>

    // 수정

    // 삭제

    // 조회

}