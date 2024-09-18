package org.example.auth.infrastructure.file

import org.example.auth.domain.cms.entity.PostsAttachFile
import org.springframework.web.multipart.MultipartFile
import java.io.File

interface FileHandler {
    /**
     * 파일을 handler가 지정한 방식으로 저장한다.
     */
    fun uploadFile(part: MultipartFile): PostsAttachFile

    /**
     * 파일이 저장된 url 경로를 가져온다.
     */
    fun getFilePath(url: String): File

    /**
     * 파일을 handler가 지정한 방식으로 삭제한다.
     */
    fun deleteFile(fileKey: String): Boolean
}
