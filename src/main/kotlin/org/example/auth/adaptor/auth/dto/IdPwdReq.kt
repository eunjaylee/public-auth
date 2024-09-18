package org.example.auth.adaptor.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

class IdPwdReq(
    // TODO enum
    // id, pwd
    @Schema(description = "id / pwd 찾는 것")
    @field:NotNull
    val findType: String,
    @field:NotNull
    @Schema(description = "가입자 이름")
    val name: String,
    // cellphone, email 실제 값
    @field:NotNull
    @Schema(description = "이메일 또는 휴대전화번호")
    val to: String,
    // cellphone, email
    @field:NotNull
    @Schema(description = "이메일 또는 휴대전화")
    val sendType: String,
)
