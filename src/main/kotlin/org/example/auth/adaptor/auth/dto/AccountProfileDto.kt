package org.example.auth.adaptor.auth.dto

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.example.auth.adaptor.Masked
import org.example.auth.adaptor.MaskedSerializer
import org.example.auth.domain.auth.entity.UserAdditionalInfo
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

class AccountProfileDto(
    @Schema(description = "회원 고유 키값")
    var userSeq: Long = 0,
    @Schema(description = "회원이름 - 실명")
    var name: String = "",
    @field:Email
    @Schema(description = "email", example = "test@test.com")
    val email: String = "",
    @Schema(description = "핸드폰 번호")
    @field:Size(min = 9, max = 12, message = "{Size}")
    @field:Pattern(regexp = "[0-9]+")
    var cellPhone: String? = null,
    @Schema(description = "생년월일")
    @field:Size(min = 9, max = 12, message = "{Size}")
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    var birthDate: String? = null,
    @Schema(description = "성별")
    var gender: String? = null,
    @Schema(description = "국적")
    var nationalInfo: String? = null,
    @Schema(description = "통신사")
    var moblieCorp: String? = null,
    @Schema(description = "CI")
    @Masked('*', "ALL")
    @JsonSerialize(using = MaskedSerializer::class)
    var ci: String? = null,
    @Schema(description = "DI")
    @Masked('*', "ALL")
    @JsonSerialize(using = MaskedSerializer::class)
    var di: String? = null,
    @Schema(description = "회원 추가정보 - 주소")
    var userAdditionalInfo: UserAdditionalInfo = UserAdditionalInfo(),
    @Schema(description = "회원 가입일시")
    var createAt: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "회원 수정일시")
    var updateAt: LocalDateTime? = null,
    @Schema(description = "회원 정보")
    var accountDto: AccountDto? = null,
)
