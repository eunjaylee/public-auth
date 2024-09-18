package org.example.auth.adaptor.auth.dto

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import org.example.auth.adaptor.Masked
import org.example.auth.adaptor.MaskedSerializer
import org.example.auth.adaptor.validator.ClassValidPassword
import org.example.auth.domain.auth.entity.UserDetail
import org.example.auth.domain.auth.entity.UserPersonalInfo
import org.example.auth.domain.auth.entity.auth.UserAuthorities
import org.example.auth.domain.auth.entity.enumtype.UserStatus
import org.example.auth.infrastructure.util.modelMapTo
import java.io.Serializable
import java.time.LocalDateTime

@Schema(description = "회원 정보")
@ClassValidPassword(
    message = "Password must be valid if weakPassword Y",
    minLength = 8,
    maxLength = 20,
    hasUpperCase = true,
    hasLowerCase = true,
    hasDigit = true,
    hasSpecialChar = true,
    conditionField = "weakPasswordYn", // 조건 필드 지정
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "userSeq")
class AccountDto(
    @Schema(description = "회원 고유 키값")
    val userSeq: Long = 0,
    @Schema(description = "회원 정보 수정 ID")
    val version: Long = 0,
    @field:Size(min = 4, max = 10, message = "{Size}")
    @Schema(description = "회원 id", example = "1")
    val userId: String = "",
    @Schema(description = "회원비밀번호")
    @Masked('*', "ALL")
    @JsonSerialize(using = MaskedSerializer::class)
    var userPwd: String = "",
    @Schema(description = "회원이름 - 닉네임")
    var userName: String = "",
    @Schema(description = "회원상태")
    var userStatus: UserStatus = UserStatus.NORMAL,
    @Schema(description = "회원 권한 목록")
    var userAuthorities: MutableSet<UserAuthorities> = mutableSetOf(),
    @Schema(description = "가입 추천인 코드")
    var recommendCode: String = "",
    @Schema(description = "내 추천인 코드")
    var myRecommendCode: String = "",
    @Schema(description = "계정 잠김여부")
    var accountLocked: Boolean = false,
    @Schema(description = "프로파일 이미지")
    var imageUrl: String? = null,
    @Schema(description = "회원 개인 정보")
    var accountProfileDto: AccountProfileDto? = null,
    @Schema(description = "로그인 실패 횟수")
    var failedLoginAttempts: Int = 0,
    @Schema(description = "가입일시")
    var createAt: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "수정일시")
    var updateAt: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "최종 로그인일시")
    var lastLoginDtime: LocalDateTime? = null,
) : Serializable {
    constructor(userDetail: UserDetail, userPersonalInfo: UserPersonalInfo) : this (
        userDetail.userSeq,
        userDetail.version,
        userDetail.userId,
        userDetail.userPwd,
        userDetail.userName,
        userDetail.userStatus,
        userDetail.userAuthorities,
        userDetail.recommendCode,
        userDetail.myRecommendCode,
        userDetail.accountLocked,
        userDetail.imageUrl,
        userPersonalInfo.modelMapTo<AccountProfileDto>(),
        userDetail.failedLoginAttempts,
        userDetail.createAt,
        userDetail.updateAt,
        userDetail.lastLoginDtime,
    )

    @Schema(description = "이메일 검증여부")
    var emailVerified: Boolean? = true

    @Schema(description = "약한 비밀번호 허용")
    var weakPasswordYn: String = "N"
}
