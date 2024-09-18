package org.example.auth.domain.auth.entity.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Convert
import org.example.auth.infrastructure.cipher.AesCipher

@Convert
class StringCryptoConverter(
    val cipher: AesCipher,
) : AttributeConverter<String, String> {
    /**
     * 파라미터가 NULL일수 있으니 ?를 붙여주자
     */
    override fun convertToDatabaseColumn(attribute: String?): String {
        return cipher.encrypt(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): String {
        return cipher.decrypt(dbData)
    }
}
