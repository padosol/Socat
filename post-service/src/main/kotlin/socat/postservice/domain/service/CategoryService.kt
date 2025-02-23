package socat.postservice.domain.service

import org.springframework.stereotype.Service
import socat.postservice.application.port.output.CategoryPersistencePort


@Service
class CategoryService(
    private val categoryPersistencePort: CategoryPersistencePort
) {

}
