package org.example.auth.adaptor.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

class CheckCodeReq(
    // TODO enum
    @field:NotNull
    @Schema(description = "이메일 또는 휴대전화")
    val sendType: String,
    @Schema(description = "가입자 이름")
    val name: String? = null,
    val checkCode: String? = null,
    @field:NotNull
    @Schema(description = "이메일 또는 휴대전화번호")
    val to: String,
    @Schema(description = "find / register")
    @field:NotNull
    val type: String = "register",
) {
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email.matches(emailRegex.toRegex())
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneRegex = "^\\d{2,3}-\\d{3,4}-\\d{4}$"
        return phoneNumber.matches(phoneRegex.toRegex())
    }

    fun verifyTo(): Boolean {
        return when (sendType) {
            "email" -> isValidEmail(to)
            "cellPhone" -> isValidPhoneNumber(to)
            else -> true
        }
    }
}
