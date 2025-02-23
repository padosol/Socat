package socat.postservice.application.port.input.post

import org.springframework.web.multipart.MultipartFile

interface UploadFileUseCase {
    fun uploadFile(file: MultipartFile): String

}