package org.example.auth.adaptor.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

class UserSearchParamReq {
    @Schema(description = "검색할 유져명")
    var userName: String? = null

    @Schema(description = "검색할 이메일주소")
    var email: String? = null

    @Schema(description = "검색할 전화번호")
    var cellPhone: String? = null

    @Schema(description = "검색할 유저 ID")
    var userId: String? = null

    @Schema(description = "가입 시작일")
    var startDt: String? = null

    @Schema(description = "가입 종료일")
    var endDt: String? = null

    @Schema(description = "추천인 코드")
    var recommendCode: String? = null
}
