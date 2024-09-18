package org.example.auth.adaptor.cms.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "게시판 정보")
class BoardDto(
    @Schema(description = "게시판 타입", example = "FAQ")
    val boardType: String,
    @Schema(description = "게시판 명칭", example = "무엇이든 물어보세요")
    var boardName: String,
    @Schema(description = "게시판 사용가능여부", example = "Y")
    var useYn: String = "Y",
    @Schema(description = "게시판 생성일시", example = "Y")
    var createAt: LocalDateTime = LocalDateTime.now(),
)
