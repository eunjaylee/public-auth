package org.example.auth.adaptor.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "true/false 반환")
class BooleanResponse(
    @Schema(description = "code", example = "FAQ")
    val code: String = "",
    @Schema(description = "status", example = "TRUE")
    val status: Boolean = true,
)
