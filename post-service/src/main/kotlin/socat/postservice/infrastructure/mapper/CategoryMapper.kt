package socat.postservice.infrastructure.mapper

import socat.postservice.domain.model.Category
import socat.postservice.domain.model.Post
import socat.postservice.infrastructure.persistence.entity.CategoryEntity

class CategoryMapper {

    companion object {
        fun entityToDomain(categoryEntity: CategoryEntity): Category {
            return Category(
                categoryId = categoryEntity.categoryId,
                categoryName = categoryEntity.categoryName
            )
        }

        fun domainToEntity(category: Category): CategoryEntity {
            return CategoryEntity(
                categoryId = category.categoryId,
                categoryName = category.categoryName
            )
        }
    }
}