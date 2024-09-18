package org.example.auth.infrastructure.util

import org.apache.commons.lang3.builder.ReflectionToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.modelmapper.spi.MatchingStrategy

class Extension

fun Any.toStringByReflection(
    exclude: List<String> = listOf(),
    mask: List<String> = listOf(),
): String {
    val style = ToStringStyle.MULTI_LINE_STYLE
    return ReflectionToStringBuilder(this, style).toString()
}

val modelMapper =
    ModelMapper().apply {
        configuration.matchingStrategy = MatchingStrategies.STRICT
        configuration.isFieldMatchingEnabled = true
        configuration.setSkipNullEnabled(true) // null매핑은 스킵(기본값을 보장)
        configuration.fieldAccessLevel = org.modelmapper.config.Configuration.AccessLevel.PRIVATE
    }

// 확장 함수 정의
inline fun <reified D> Any.modelMapTo(strategies: MatchingStrategy = MatchingStrategies.STRICT): D {
    if (strategies == MatchingStrategies.STRICT) return modelMapper.map(this, D::class.java)

    val localMapper =
        ModelMapper().apply {
            configuration.matchingStrategy = strategies
            configuration.isFieldMatchingEnabled = true
            configuration.setSkipNullEnabled(true)
            configuration.fieldAccessLevel = org.modelmapper.config.Configuration.AccessLevel.PRIVATE
        }

    return localMapper.map(this, D::class.java)
}

// 확장 함수 정의 (리스트 변환용)
inline fun <reified D> Collection<*>.modelMapToList(): List<D> {
    return this.filter { isNotEmpty() }.map { it!!.modelMapTo<D>() }
}
