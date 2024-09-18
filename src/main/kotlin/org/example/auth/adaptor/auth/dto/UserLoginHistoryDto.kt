package org.example.auth.adaptor.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

class UserLoginHistoryDto(
    @Schema(description = "회원 고유 키값")
    val userSeq: Long = 0,
    @Schema(description = "장치 타입")
    val deviceType: String = "",
    @Schema(description = "os")
    val deviceOs: String = "",
    @Schema(description = "ip주소")
    val deviceIp: String = "",
    @Schema(description = "브라우져 종류")
    val browser: String = "",
    @Schema(description = "히스토리 키 값")
    val userLoginHistorySeq: Long = 0,
    @Schema(description = "로그인 일시")
    val loginDtime: LocalDateTime = LocalDateTime.now(),
)
