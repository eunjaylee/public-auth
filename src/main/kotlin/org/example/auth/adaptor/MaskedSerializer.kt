package org.example.auth.adaptor

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.ContextualSerializer

class MaskedSerializer() : JsonSerializer<String>(), ContextualSerializer {
    private var maskWith: Char = '*'
    private var option: String = "PRE"

    constructor(maskWith: Char, option: String) : this() {
        this.maskWith = maskWith
        this.option = option
    }

    override fun serialize(
        value: String?,
        gen: JsonGenerator,
        serializers: SerializerProvider,
    ) {
        if (value == null) {
            gen.writeNull()
        } else {
            // 마스킹 처리: 절반 글자만 남기고 나머지는 마스킹 문자로 대체
            val maskedValue =
                if (value.length > 1) {
                    when (option) {
                        "PRE" -> maskWith.toString().repeat(value.length - value.length / 2) + value.substring(value.length / 2)
                        "ALL" -> maskWith.toString().repeat(value.length)
                        else -> value.substring(0, value.length / 2) + maskWith.toString().repeat(value.length - value.length / 2)
                    }
                } else {
                    maskWith.toString()
                }
            gen.writeString(maskedValue)
        }
    }

    override fun createContextual(
        prov: SerializerProvider,
        property: BeanProperty?,
    ): JsonSerializer<*> {
        val annotation = property?.getAnnotation(Masked::class.java)
        return if (annotation != null) {
            MaskedSerializer(annotation.maskWith, annotation.option) // 어노테이션 값 주입
        } else {
            this
        }
    }
}
