package socat.postservice.infrastructure.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import socat.postservice.global.exception.PostException
import socat.postservice.global.exception.PostExceptionCode
import socat.postservice.global.utils.FileValidate
import java.lang.System.*
import java.time.LocalDateTime
import java.time.LocalDateTime.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.*

@Component
class S3FileManagement(
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String,
    private val amazonS3: AmazonS3,
) {
    companion object {
        const val TYPE_IMAGE = "image"
    }
    fun uploadFile(multipartFile: MultipartFile): String {
        val originalFilename = multipartFile.originalFilename
            ?: throw PostException(PostExceptionCode.WRONG_FORMAT_FILE_NAME)
        FileValidate.checkImageFormat(originalFilename)
        val fileName = createNewFileName(originalFilename)
        val objectMetadata = setFileDateOption(
            type = TYPE_IMAGE,
            file = getFileExtension(originalFilename),
            multipartFile = multipartFile
        )
        amazonS3.putObject(bucket, fileName, multipartFile.inputStream, objectMetadata)
        return fileName
    }

    fun getFile(fileName: String): String {
        return amazonS3.getUrl(bucket,fileName).toString()
    }

    fun delete(fileName: String) {
        amazonS3.deleteObject(bucket,fileName)
    }

    private fun getFileExtension(fileName: String): String {
        val extensionIndex = fileName.lastIndexOf('.')
        return fileName.substring(extensionIndex + 1)
    }

    private fun setFileDateOption(
        type: String,
        file: String,
        multipartFile: MultipartFile
    ): ObjectMetadata {
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = "${type}/${getFileExtension(file)}"
        objectMetadata.contentLength = multipartFile.inputStream.available().toLong()
        return objectMetadata
    }

    private fun createNewFileName(fileName: String): String {
        val ofPattern = ofPattern("yyyyMMddHHmmss")
        val now = now().format(ofPattern)

        val nanoTime = nanoTime()

        return "${now}-${nanoTime}-${fileName}"
    }
}