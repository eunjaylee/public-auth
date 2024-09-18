package org.example.auth.adaptor.validator

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER) // 어노테이션이 필드나 메서드 파라미터에 적용됨
@Retention(AnnotationRetention.RUNTIME) // 런타임까지 유지
@Constraint(validatedBy = [FieldPasswordValidator::class]) // PasswordValidator와 연결
annotation class FieldValidPassword(
    val message: String = "Invalid password", // 기본 에러 메시지
    val minLength: Int = 8, // 최소 길이
    val maxLength: Int = 20, // 최대 길이
    val hasUpperCase: Boolean = true, // 대문자 포함 여부
    val hasLowerCase: Boolean = true, // 소문자 포함 여부
    val hasDigit: Boolean = true, // 숫자 포함 여부
    val hasSpecialChar: Boolean = true, // 특수 문자 포함 여부
    val groups: Array<KClass<*>> = [], // 검증 그룹
    val payload: Array<KClass<out Any>> = [], // 메타 정보
)
