package socat.postservice.infrastructure.persistence.category

import org.springframework.stereotype.Repository
import socat.postservice.application.port.output.CategoryPersistencePort
import socat.postservice.domain.model.Category

@Repository
class CategoryRepository(
) : CategoryPersistencePort{
    override fun save(category: Category): Category {
        TODO("Not yet implemented")
    }

    override fun remove(category: Category) {
        TODO("Not yet implemented")
    }

    override fun findById(categoryId: String): Category {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Category> {
        TODO("Not yet implemented")
    }
}