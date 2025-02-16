package org.prodcontest.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import javax.imageio.ImageIO
import kotlin.io.path.deleteIfExists


@Service
class ImageStorageService {
    @Value("\${ad.images.upload-dir}")
    private val uploadDir: String? = null

    fun storeImage(file: MultipartFile): String {
        file.inputStream.use { input ->
            try {
                ImageIO.read(input)
            } catch (ex: Exception) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST)
            }
        }

        val uploadPath = File(uploadDir!!)
        if (!uploadPath.exists()) {
            uploadPath.mkdirs()
        }

        val fileName = "${UUID.randomUUID()}_${file.originalFilename}"
        val destination = File(uploadPath, fileName)

        file.transferTo(destination)

        return fileName
    }

    fun deleteImage(fileName: String) {
        val filePath: Path = Paths.get(uploadDir!!).resolve(fileName).normalize()
        filePath.deleteIfExists()
    }

    fun loadImage(fileName: String): Resource {
        val filePath: Path = Paths.get(uploadDir!!).resolve(fileName).normalize()
        val resource: Resource = UrlResource(filePath.toUri())
        if (resource.exists() || resource.isReadable) {
            return resource
        } else {
            throw RuntimeException("Could not read the file")
        }
    }
}