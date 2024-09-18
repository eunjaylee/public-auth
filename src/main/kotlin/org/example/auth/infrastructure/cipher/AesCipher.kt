package org.example.auth.infrastructure.cipher

interface AesCipher {
    /**
     * 평문을 암호화한 값을 리턴한다.
     */
    fun encrypt(attribute: String?): String

    /**
     * 암호문을 복호화한 값을 리턴
     */
    fun decrypt(dbData: String?): String
}
