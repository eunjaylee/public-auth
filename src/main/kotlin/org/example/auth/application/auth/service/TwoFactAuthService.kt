package org.example.auth.application.auth.service

interface TwoFactAuthService {
    /**
     * 이메일을 가지고 중복체크를 한다면??
     */
    fun checkVerifyEmail(to: String): String

    /**
     * 이메일 중복코드 검증
     */
    fun checkVerifyCode(to: String): String?

    /**
     * 비밀번호 변경 관련 임시 링크 메일을 발송한다.
     */
    fun sendLink()

    /**
     * 해당 링크로 요청이 들어오면(유효시간내) 회원 비밀번호 변경창으로 이동한다.
     */
    fun verifyLink()
}
