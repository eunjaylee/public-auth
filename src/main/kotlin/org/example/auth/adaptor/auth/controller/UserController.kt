package org.example.auth.adaptor.auth.controller

import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import mu.KLogging
import org.example.auth.adaptor.auth.dto.AccountDto
import org.example.auth.adaptor.auth.dto.CheckCodeReq
import org.example.auth.adaptor.auth.dto.IdPwdReq
import org.example.auth.adaptor.auth.dto.UserLoginHistoryDto
import org.example.auth.adaptor.dto.BooleanResponse
import org.example.auth.adaptor.dto.MessageResponse
import org.example.auth.application.auth.service.UserService
import org.example.auth.domain.auth.Account
import org.example.auth.domain.auth.entity.enumtype.CommonCode
import org.example.auth.infrastructure.util.modelMapTo
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService,
    private val messageSource: MessageSource,
//    private val authorityService : AuthorityService,
//    val twoFactAuthService : TwoFactAuthService,
//    val notifyServiceFactory : NotifyServiceFactory
) {
    companion object : KLogging()

    /**
     * 회원 정보를 보여준다.
     */
    @Operation(summary = "개인 본인 정보 확인")
    @GetMapping("/userInfo")
    fun getUserInfo(
        @AuthenticationPrincipal user: Account?,
    ): AccountDto {
        user ?: throw Exception("로그인 되어 있지 않습니다.")
        return (userService.getUser(user.userSeq) ?: user).modelMapTo()
    }

    /**
     * 회원 정보를 보여준다.
     */
    @Operation(summary = "개인 세션 어트리뷰트")
    @GetMapping("/userAttr")
    fun getUserAttr(request: HttpServletRequest): Map<String, Any> {
        val session = request.session
        val attributes = mutableMapOf<String, Any>()

        // 세션의 모든 속성을 Map에 추가
        val attributeNames = session.attributeNames
        while (attributeNames.hasMoreElements()) {
            val attributeName = attributeNames.nextElement()
            attributes[attributeName] = session.getAttribute(attributeName)
        }

        return attributes
    }

    /**
     * ID 중복 확인
     */
    @Operation(summary = "ID 중복여부를 확인한다.")
    @GetMapping("/checkId/{userId}")
    fun checkId(
        @PathVariable userId: String,
    ): BooleanResponse {
        return BooleanResponse("duplicateId", userService.isNotDuplicateUserId(userId))
    }

    /**
     * 인증코드 발송
     */
    @Operation(summary = "인증 코드 발송")
    @PostMapping("/sendCode")
    fun sendCode(
        @Valid @RequestBody codeReq: CheckCodeReq,
    ): BooleanResponse {
        return BooleanResponse("sendCode", userService.sendVerifyCode(codeReq))
    }

    /**
     * 인증코드 체크
     */
    @Operation(summary = "인증 코드 검증")
    @PostMapping("/checkVerifyCode")
    fun checkCode(
        @Valid @RequestBody codeReq: CheckCodeReq,
        request: HttpServletRequest,
    ): BooleanResponse {
        val checkCode = userService.checkVerifyCode(codeReq)

        if (checkCode) {
            // 세션엔 한번에 하나의 인증정보만을 가지자.. 찾기든 등록이든
            request.session.setAttribute("checkVerify", "${codeReq.type}:${codeReq.to}")
        }

        return BooleanResponse("checkCode", checkCode)
    }

    /**
     * 아이디/비밀번호 찾기
     */
    @Operation(summary = "아이디 비밀번호 찾기")
    @PostMapping("/find/{findType}")
    fun findIdPwd(
        @PathVariable findType: String,
        @RequestBody idPwdReq: IdPwdReq,
        request: HttpServletRequest,
    ): MessageResponse {
        // 2차 인증 여부는 컨트롤러의 역할일까?
        verifyCodeCheck(request, CheckCodeReq(type = "find", to = idPwdReq.to, sendType = idPwdReq.sendType))

        var message =
            if (findType == "id") {
                // ID 찾으면 2차 인증 후 찾은 ID를 보여준다.
                messageSource.getMessage("user.find_id", arrayOf(userService.idFind(idPwdReq)), LocaleContextHolder.getLocale())
            } else {
                // 패스워드 변경처리를 위한 임시 IdCode를 반환한다.
                messageSource.getMessage("user.find_pwd", arrayOf(userService.pwdFind(idPwdReq)), LocaleContextHolder.getLocale())
            }

        return MessageResponse(message)
    }

    /**
     * 비밀번호 변경
     */
    @Operation(summary = "비밀번호 변경 처리")
    @PostMapping("/changePwd")
    fun changePwd(
        request: HttpServletRequest,
        @AuthenticationPrincipal user: Account?,
        newPassword: String,
        idCode: String?,
    ): String {
        //  verifyCodeCheck(request, CheckCodeReq(type="find", to=it.accountInfo!!.email, sendType="email"))
        user?.let {
            it.userPwd = newPassword
            return userService.changePwd(it).userId
        }

        // 인증 코드가 제공된 경우
        idCode?.let {
            return userService.changePwd(newPassword, it).userId
        }

        // 사용자 정보도 없고 인증 코드도 제공되지 않은 경우
        throw IllegalStateException("로그인 되어 있지 않거나 인증받지 않은 상태임..")
    }

    private fun verifyCodeCheck(
        request: HttpServletRequest,
        checkCodeReq: CheckCodeReq,
    ) {
        val sessionVerifyCode = request.session.getAttribute("checkVerify") ?: throw Exception("2차 인증이 되지 않았습니다.")
        if (sessionVerifyCode.toString() != "${checkCodeReq.type}:${checkCodeReq.to}") {
            throw Exception("2차 인증이 되지 않았습니다.")
        }
    }

    /**
     * 회원가입
     */
    @Operation(summary = "회원 가입처리")
    @PostMapping("/signup", "/regist")
    fun register(
        request: HttpServletRequest,
        @Valid @RequestBody accountDto: AccountDto,
    ): AccountDto {
        verifyCodeCheck(request, CheckCodeReq(type = "register", to = accountDto.accountProfileDto!!.email, sendType = "email"))
        return userService.registUser(accountDto, accountDto.accountProfileDto).modelMapTo()
    }

    /**
     * 회원 정보를 수정한다.
     */
    @Operation(summary = "개인 본인 정보 수정")
    @PostMapping("/profile")
    fun modifyUserInfo(
        @Valid @RequestBody user: AccountDto,
    ): MessageResponse {
        userService.modifyUserInfo(user)
        return MessageResponse(message = messageSource.getMessage("update_user_profile", null, LocaleContextHolder.getLocale()))
    }

    @Operation(summary = "로그인 이력 조회")
    @PostMapping("/login-history")
    fun loginHistory(
        @AuthenticationPrincipal user: Account,
        @PageableDefault(page = 0, size = 20) pageable: Pageable,
    ): Slice<UserLoginHistoryDto> {
        return userService.getLoginHistory(user.userSeq, pageable).map { it.modelMapTo() }
    }

    /**
     * 추천인 회원 정보를 보여준다.
     */
    @Operation(summary = "추천인 회원 조회")
    @GetMapping("/recommend")
    fun recommendList(
        @AuthenticationPrincipal user: Account?,
    ): List<AccountDto> {
        user ?: throw Exception("로그인 되어 있지 않습니다.")
        return (userService.getRecommendList(user.userSeq) ?: user).modelMapTo()
    }

    /**
     * 마케팅 동의
     */
    @Operation(summary = "마케팅 동의 신청")
    @GetMapping("/marketting/{action}")
    @Deprecated("")
    fun modifyMarketting(
        @PathVariable action: String,
        @AuthenticationPrincipal user: Account,
    ): MessageResponse {
        when (action) {
            "agree" -> userService.modifyMarketingInfo(user, "Y")
            "disagree" -> userService.modifyMarketingInfo(user, "N")
            else -> throw Exception(CommonCode.REQUEST_NOT_VALID.toString())
        }
        return MessageResponse(message = messageSource.getMessage("marketing_message", null, LocaleContextHolder.getLocale()))
    }
}
