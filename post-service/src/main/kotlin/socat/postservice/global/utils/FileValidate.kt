package socat.postservice.global.utils

import socat.postservice.global.exception.PostException
import socat.postservice.global.exception.PostExceptionCode

class FileValidate {

    companion object {
        private val IMAGE_EXTENSIONS: List<String> = listOf("jpg", "png", "gif", "webp")
        fun checkImageFormat(fileName: String) {
            val extensionIndex = fileName.lastIndexOf('.')
            if(extensionIndex == -1) {
                throw PostException(PostExceptionCode.FILE_EXTENSION_NOT_EXIST)
            }
            val extension = fileName.substring(extensionIndex + 1)
            require(IMAGE_EXTENSIONS.contains(extension)) {
                throw PostException(PostExceptionCode.FILE_EXTENSION_NOT_SUPPORT)
            }
        }
    }
}