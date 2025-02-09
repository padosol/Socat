package socat.postservice.application.port.input

import org.springframework.web.multipart.MultipartFile

interface UploadFileUseCase {
    fun uploadFile(file: MultipartFile): String

}