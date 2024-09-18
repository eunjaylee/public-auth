package org.example.auth.application.auth.service

import org.example.auth.adaptor.auth.dto.*
import org.example.auth.domain.auth.Account
import org.example.auth.domain.auth.entity.UserDetail
import org.example.auth.domain.auth.entity.UserLoginHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import java.time.LocalDate

interface UserService {
    /**
     * 회원 등록
     */
    fun registUser(account: AccountDto): UserDetail

    /**
     * 회원 등록
     */
    fun registUser(
        account: AccountDto,
        accountProfileDto: AccountProfileDto?,
    ): UserDetail

    /**
     * 회원 상세조회 -> 개인정보 수정등의 목적으로
     */
    fun getUser(userSeq: Long): AccountDto?

    /**
     * 회원 정보 수정
     * 휴대전화, 이메일등
     */
    fun modifyUserInfo(user: AccountDto): Boolean

    /**
     * ID 중복검증 --> true면 중복 id 있음
     */
    fun isNotDuplicateUserId(userId: String): Boolean

    /**
     * 마케팅 동의 정보 수정
     *
     */
    fun modifyMarketingInfo(
        userInfo: Account,
        marketingYn: String,
    ): Boolean

    fun sendVerifyCode(checkCodeReq: CheckCodeReq): Boolean

    fun checkVerifyCode(codeReq: CheckCodeReq): Boolean

    fun idFind(idPwdReq: IdPwdReq): String

    fun pwdFind(idPwdReq: IdPwdReq): String

    fun getLoginHistoryList(userSeq: Long): Page<UserLoginHistory>

    fun lastLoginHistory(): UserLoginHistory

    fun getLoginHistory(
        userSeq: Long,
        page: Pageable,
    ): Slice<UserLoginHistory>

    /**
     * 패스워드 변경이력 확인 - 별도 테이블로 패스워드의 변경이력을 가지고??? 로그인 히스토리에서 가지고?
     */
    fun passwordChangeDay(): LocalDate

    /**
     * 패스워드 변경
     */
    fun changePwd(account: Account): UserDetail

    /**
     * 패스워드 변경
     */
    fun changePwd(
        newPassword: String,
        idCode: String,
    ): UserDetail

    /**
     *  회원 검색..
     */
    fun userSearch(
        param: UserSearchParamReq,
        page: Pageable,
    ): Page<AccountDto>

    /**
     * 추천인 목록
     */
    fun getRecommendList(userSeq: Long): Slice<AccountDto>?
}
