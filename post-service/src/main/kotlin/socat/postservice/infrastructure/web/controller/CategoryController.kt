package socat.postservice.infrastructure.web.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import socat.postservice.global.dto.APIResponse
import socat.postservice.infrastructure.web.dto.request.category.CreateCategoryDTO
import socat.postservice.infrastructure.web.dto.response.category.CategoryResponse

@RestController
@RequestMapping
class CategoryController : SwaggerCategory{
    override fun create(createCategoryDTO: CreateCategoryDTO): ResponseEntity<APIResponse<CategoryResponse>> {
        TODO("Not yet implemented")
    }

}