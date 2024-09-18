package org.example.auth.infrastructure.cipher

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.lang.Exception
import java.security.Key
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Service
@Primary
class AesCipherImpl : AesCipher {
    //    @Value("\${j-tech.security.algorithm}")
//    lateinit var algorithm : String
//
//    @Value("\${j-tech.security.key}")
//    lateinit var bbrickKey : String

    companion object {
        const val algorithm: String = "AES/ECB/PKCS5Padding"
        const val cipherKey = "1234567890123456"
    }

    override fun encrypt(attribute: String?): String {
        if (attribute == null) return ""
        val key: Key = SecretKeySpec(cipherKey.toByteArray(), "AES")
        return try {
            val cipher = Cipher.getInstance(algorithm)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            String(Base64.getEncoder().encode(cipher.doFinal(attribute.toByteArray())))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    override fun decrypt(dbData: String?): String {
        if (dbData == null) return ""

        val key: Key = SecretKeySpec(cipherKey.toByteArray(), "AES")
        return try {
            val cipher = Cipher.getInstance(algorithm)
            cipher.init(Cipher.DECRYPT_MODE, key)
            String(cipher.doFinal(Base64.getDecoder().decode(dbData)))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
