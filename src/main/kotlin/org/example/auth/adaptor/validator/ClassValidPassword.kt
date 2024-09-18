package org.example.auth.adaptor.validator

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ClassPasswordValidator::class])
annotation class ClassValidPassword(
    val message: String = "Invalid password", // 기본 에러 메시지
    val minLength: Int = 8, // 최소 길이
    val maxLength: Int = 20, // 최대 길이
    val hasUpperCase: Boolean = true, // 대문자 포함 여부
    val hasLowerCase: Boolean = true, // 소문자 포함 여부
    val hasDigit: Boolean = true, // 숫자 포함 여부
    val hasSpecialChar: Boolean = true, // 특수 문자 포함 여부
    val groups: Array<KClass<*>> = [], // 검증 그룹
    val payload: Array<KClass<out Any>> = [], // 메타 정보
    val conditionField: String = "",
)
