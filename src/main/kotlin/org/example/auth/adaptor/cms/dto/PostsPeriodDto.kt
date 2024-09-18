package org.example.auth.adaptor.cms.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "게시글 팝업기간")
data class PostsPeriodDto(
    @Schema(description = "팝업 시작일시", example = "2024.08.01")
    var startedAt: Date? = null,
    @Schema(description = "팝업 종료일시", example = "2024.08.01")
    var finishedAt: Date? = null,
)
