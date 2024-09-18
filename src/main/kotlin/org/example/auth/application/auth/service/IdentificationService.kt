package org.example.auth.application.auth.service

import jakarta.servlet.http.HttpSession

interface IdentificationService {
    /**
     * Nice에 인증을 위한 암호화 파라미터를 생성
     */
    fun encCipherData(
        session: HttpSession,
        reqNum: String?,
    ): String

    /**
     * Nice에서 전달받은 결과를 복호화 해서 개인정보로 변환한다.
     */
    @Throws(Exception::class)
    fun decEncodeData(
        encodeData: String,
        reqSeq: HttpSession,
    ): Map<String, String>

    fun getAmericanAge(birthDate: String?): Int
}
