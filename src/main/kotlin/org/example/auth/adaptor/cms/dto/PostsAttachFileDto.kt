package org.example.auth.adaptor.cms.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "첨부파일 정보를 담고 있는 객체")
class PostsAttachFileDto(
    @Schema(description = "첨부파일 번호", example = "1")
    var attachSeq: Long = 0,
    @Schema(description = "첨부파일 키", example = "fjwepfe")
    var fileKey: String,
    @Schema(description = "게시글 번호", example = "1")
    var postSeq: Long = 0,
    @Schema(description = "첨부파일 명", example = "example.txt")
    var fileName: String,
    @Schema(description = "첨부파일 경로", example = "/attach/test/file")
    var filePath: String,
    @Schema(description = "첨부파일 크기 byte", example = "100")
    var fileSize: Long,
)
