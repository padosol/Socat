package socat.postservice.application.port.output

import socat.postservice.domain.model.Category

interface CategoryPersistencePort {

    fun save(category: Category): Category

    fun remove(category: Category)

    fun findById(categoryId: String): Category

    fun findAll(): List<Category>
}