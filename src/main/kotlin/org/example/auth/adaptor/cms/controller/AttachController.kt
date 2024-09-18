package org.example.auth.adaptor.cms.controller

// import jakarta.servlet.http.HttpServletRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.auth.adaptor.cms.dto.PostsAttachFileDto
import org.example.auth.application.cms.service.AttachService
import org.example.auth.infrastructure.util.modelMapTo
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Tag(description = "첨부파일 관련", name = "Attach")
@RestController
@RequestMapping("/api/v1/attach")
class AttachController(
    private val attachService: AttachService,
) {
    /**
     * 파일 등록 / 수정
     */
    @Operation(summary = "파일저장")
    @PostMapping("/file")
    fun saveAttachFile(
        @Parameter(name = "file", description = "첨부파일", required = true)
        @RequestParam("file") files: List<MultipartFile>,
    ): List<PostsAttachFileDto> {
        return attachService.uploadFiles(files).map { it.modelMapTo<PostsAttachFileDto>() }
    }

    @Operation(summary = "파일저장")
    @PostMapping("/fileAsync2")
    fun saveAsyncAttachFile2(
        @Parameter(name = "file", description = "첨부파일", required = true)
        @RequestParam("file") filePart: Flux<FilePart>,
    ): Mono<List<PostsAttachFileDto>> {
        return attachService.uploadAsyncFile(filePart).map { fileList -> fileList.map { it.modelMapTo<PostsAttachFileDto>() } }
    }

    @Operation(summary = "파일 목록 조회")
    @GetMapping("/{postSeq}")
    fun getPostsFiles(
        @Parameter(name = "postSeq", description = "게시글 번호", `in` = ParameterIn.PATH, required = true)
        @PathVariable postSeq: Long,
    ): List<PostsAttachFileDto> {
        return attachService.getPostsFile(postSeq).map { it.modelMapTo<PostsAttachFileDto>() }
    }
}
