package org.example.auth.adaptor.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "메시지 반환")
data class MessageResponse(
    @Schema(description = "message", example = "처리 결과 메시지")
    val message: String = "",
    @Schema(description = "data")
    val data: Any? = null,
)
