package org.example.auth.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.RequestCache
import org.springframework.stereotype.Component

@Component
class Oauth2SuccessHandler : AuthenticationSuccessHandler {
    private val requestCache: RequestCache = HttpSessionRequestCache()
    private val redirectStrategy: RedirectStrategy = DefaultRedirectStrategy()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?,
    ) {
//        OAuth2LoginAuthenticationFilter
//        DefaultOAuth2UserService

        var loginUser: OAuth2User = authentication!!.principal as OAuth2User

        println(loginUser)

        val username = loginUser.getAttribute<String>("preferred_username") // 토큰에서 필요한 정보만 추려서 가져온다.
        val provider = loginUser.getAttribute<String>("provider") // 쇼셜 인증 정보

        println("55555555555555555555")
        println(username)
        println(provider)
        println("55555555555555555555")

//
//
//

//
//        val id = loginUser.getAttribute<String>("id")
//
//        println(id)
//        println(username)
//        println(provider)

//        val username = authentication!!.name
//        val user: UserDetail? = userDao.findByUserId(username).orElse(null)

//        if (user != null) {
//            user.resetFailedLoginAttempts()
//            userDao.save(user)
//        }
//
//        val userDetail = SecurityContextHolder.getContext().authentication.principal as Account
//        var uh = setUserAgent(userDetail.userSeq, request!!)
//
//
//        // userAgent값을 저장한다.
//        userLoginHistoryDao.save(uh)
//
//
//        // 요청이 REST API인지 확인 (예: Accept 헤더로 구분)
//        if (request!!.getHeader("Accept").contains("application/json")) {
//            handleRestApiSuccess(response!!, authentication)
//        } else {
//            val savedRequest = requestCache.getRequest(request, response)
//            handleWebSuccess(request, response!!, authentication, savedRequest)
//        }
    }
//
//    protected fun setUserAgent(userSeq : Long, request: HttpServletRequest) : UserLoginHistory {
//
//        val userAgentString: String = request.getHeader("User-Agent") ?: "Unknown"
//
//        val userAgent: UserAgent = UserAgent.parseUserAgentString(userAgentString)
//        val deviceIp: String = getRemoteAddr(request)
//
//        return UserLoginHistory(userSeq,
//                                deviceType = userAgent.operatingSystem.deviceType.getName(),
//                                deviceOs = userAgent.operatingSystem.getName(),
//                                browser = userAgent.browser.getName() + " " + userAgent.browserVersion,
//                                deviceIp =  deviceIp
//                                )
//
//    }
//
//    // IP 주소를 가져오는 메서드 구현
//    fun getRemoteAddr(request: HttpServletRequest): String {
//        val xfHeader = request.getHeader("X-Forwarded-For")
//        return if (xfHeader == null) {
//            request.remoteAddr
//        } else {
//            xfHeader.split(",").firstOrNull()?.trim() ?: request.remoteAddr
//        }
//    }
//
//    @Throws(IOException::class)
//    private fun handleRestApiSuccess(response: HttpServletResponse, authentication: Authentication) {
//        response.status = HttpServletResponse.SC_OK
//        response.writer.write("{\"status\":\"success\"}")
//        response.writer.flush()
//    }
//
//    @Throws(IOException::class)
//    private fun handleWebSuccess(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        authentication: Authentication,
//        savedRequest : SavedRequest
//    ) {
//        if (savedRequest != null) {
//            // 이전에 요청한 URL로 리다이렉트
//            val targetUrl = savedRequest.redirectUrl
//            redirectStrategy.sendRedirect(request, response, targetUrl)
//        } else {
//            // 기본 리다이렉트 URL로 이동 (예: 로그인 페이지)
//            redirectStrategy.sendRedirect(request, response, "/")
//        }
//    }
}
