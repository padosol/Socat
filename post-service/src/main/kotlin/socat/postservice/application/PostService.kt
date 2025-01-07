package socat.postservice.application

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import socat.postservice.application.port.input.CreatePostUseCase
import socat.postservice.application.port.input.ModifyPostUseCase
import socat.postservice.application.port.input.RemovePostUseCase

@Slf4j
@Service
class PostService : CreatePostUseCase, ModifyPostUseCase, RemovePostUseCase{
}