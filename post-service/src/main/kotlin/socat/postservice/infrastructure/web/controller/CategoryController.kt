package socat.postservice.infrastructure.web.controller

import jakarta.servlet.http.HttpServletRequest
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import socat.postservice.domain.service.CategoryService
import socat.postservice.global.dto.APIResponse
import socat.postservice.global.utils.JwtProvider
import socat.postservice.infrastructure.web.dto.request.category.CreateCategoryDTO
import socat.postservice.infrastructure.web.dto.request.category.ModifyCategoryDTO
import socat.postservice.infrastructure.web.dto.request.category.RemoveCategoryDTO
import socat.postservice.infrastructure.web.dto.response.category.CategoryResponse

@RestController
@RequestMapping
class CategoryController(
    private val jwtProvider: JwtProvider,
    private val categoryService: CategoryService
) : SwaggerCategory{

    override fun create(
        createCategoryDTO: CreateCategoryDTO,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<APIResponse<CategoryResponse>> {
        val userId = jwtProvider.getUserIdWithBearer(token)
        TODO("Not yet implemented")
    }

    override fun modify(
        modifyCategoryDTO: ModifyCategoryDTO,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<APIResponse<CategoryResponse>> {
        val userId = jwtProvider.getUserIdWithBearer(token)
        TODO("Not yet implemented")
    }

    override fun remove(
        removeCategoryDTO: RemoveCategoryDTO,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String,
    ): ResponseEntity<APIResponse<Any>> {
        val userId = jwtProvider.getUserIdWithBearer(token)
        TODO("Not yet implemented")
    }

    override fun findById(categoryId: String): ResponseEntity<APIResponse<CategoryResponse>> {
        TODO("Not yet implemented")
    }

    override fun findAll(): ResponseEntity<APIResponse<List<CategoryResponse>>> {
        TODO("Not yet implemented")
    }

}