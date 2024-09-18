package org.example.auth.infrastructure.file

import org.example.auth.domain.cms.entity.PostsAttachFile
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDate
import java.util.*

@Service
class LocalStorageFileHandler : FileHandler {
    val uploadPath = "B:/works/upload/"

    override fun uploadFile(part: MultipartFile): PostsAttachFile {
        val fileKey = UUID.randomUUID().toString()
        val filePath = "${LocalDate.now().year}/${LocalDate.now().monthValue}"

        val path: Path = Paths.get("${uploadPath}$filePath")
        Files.createDirectories(path)

        val file = File("${uploadPath}$filePath/$fileKey")
        part.transferTo(file)
        return PostsAttachFile(fileKey = fileKey, filePath = file.path, fileName = part.originalFilename, fileSize = part.size)
    }

    override fun getFilePath(url: String): File {
        return File(uploadPath + url)
    }

    override fun deleteFile(url: String): Boolean {
        return File(uploadPath + url).delete()
    }
}
