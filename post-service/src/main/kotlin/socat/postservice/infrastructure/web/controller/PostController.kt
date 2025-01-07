package socat.postservice.infrastructure.web.controller

import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.RestController
import socat.postservice.application.port.input.CreatePostUseCase
import socat.postservice.application.port.input.ModifyPostUseCase
import socat.postservice.application.port.input.RemovePostUseCase

@Slf4j
@RestController
class PostController(
        private val createPostUseCase: CreatePostUseCase,
        private val modifyPostUseCase: ModifyPostUseCase,
        private val removePostUseCase: RemovePostUseCase
) : SwaggerPostController{


}