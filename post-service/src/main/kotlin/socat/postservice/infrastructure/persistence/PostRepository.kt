package socat.postservice.infrastructure.persistence

import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Repository
import socat.postservice.application.port.output.PostPersistencePort

@Slf4j
@Repository
class PostRepository(
        private val jpaPostRepository: JpaPostRepository
) : PostPersistencePort{
}