package org.example.auth.application.cms.service

import org.example.auth.domain.cms.entity.PostsAttachFile
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.File
import java.util.*

interface AttachService {
    /**
     * 파일 저장(단건/복수건)
     */
    fun uploadFiles(files: List<MultipartFile>): List<PostsAttachFile>

    /**
     * 비동기 파일 저장
     */
    fun uploadAsyncFile(files: Flux<FilePart>): Mono<List<PostsAttachFile>>

    /**
     * 게시글에 등록된 파일 목록을 가져온다.
     */
    fun getPostsFile(postSeq: Long): List<PostsAttachFile>

    /**
     * 파일 단건을 가져온다.
     */
    fun getFileInfo(attachFileSeq: Long): Optional<PostsAttachFile>

    /**
     * 스트림으로 파일을 가져오던가... 그냥 file객체를 가져와서 쏘던가..
     */
    fun getFile(attachFileSeq: Long): File
}
