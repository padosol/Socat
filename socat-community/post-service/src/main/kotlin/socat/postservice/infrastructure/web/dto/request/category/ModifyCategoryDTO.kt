package socat.postservice.infrastructure.web.dto.request.category

data class ModifyCategoryDTO(
    val categoryId: Long,
    val categoryName: String,
)
