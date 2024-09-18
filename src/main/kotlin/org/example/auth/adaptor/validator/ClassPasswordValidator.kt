package org.example.auth.adaptor.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.example.auth.adaptor.auth.dto.AccountDto
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

class ClassPasswordValidator : ConstraintValidator<ClassValidPassword, AccountDto> {
    private lateinit var validPassword: ClassValidPassword

    override fun initialize(constraintAnnotation: ClassValidPassword) {
        this.validPassword = constraintAnnotation
    }

    override fun isValid(
        value: AccountDto,
        context: ConstraintValidatorContext,
    ): Boolean {
        val password = value.userPwd

        if (validPassword.conditionField.isNotEmpty()) {
            val targetObject = value::class
            val conditionValue = getConditionFieldValue(value, validPassword.conditionField)

            println("${validPassword.conditionField} : $conditionValue")

            if (conditionValue == "Y") {
                return true // 조건이 맞지 않으면 검증 통과
            }
        }

//        val isActive = validPassword
//        // isActive가 false인 경우 패스워드 검증을 생략
//        if (isActive == "Y") {
//            return true
//        }

        val errorMessages: ArrayList<String> = ArrayList()

        if (password.length < validPassword.minLength || password.length > validPassword.maxLength) {
            errorMessages.add("Password must be at least ${validPassword.maxLength} characters long.")
//            return false
        }

        if (validPassword.hasUpperCase && !password.any { it.isUpperCase() }) {
            errorMessages.add("Password must contain at least one uppercase letter.")
//            return false
        }

        if (validPassword.hasLowerCase && !password.any { it.isLowerCase() }) {
            errorMessages.add("Password must contain at least one lowercase letter.")
//            return false
        }

        if (validPassword.hasDigit && !password.any { it.isDigit() }) {
            errorMessages.add("Password must contain at least one digit.")
//            return false
        }

        if (validPassword.hasSpecialChar && !password.any { !it.isLetterOrDigit() }) {
            errorMessages.add("Password must contain at least one special character.")
//            return false
        }

        // 오류 메시지 추가
        if (errorMessages.size > 0) {
            context.disableDefaultConstraintViolation()
            for (errorMessage in errorMessages) {
                context.buildConstraintViolationWithTemplate(errorMessage)
                    .addConstraintViolation()
            }
            return false
        }

        return true
    }

    private fun getConditionFieldValue(
        targetObject: Any,
        conditionField: String,
    ): String? {
//        val kClass = targetObject::class
//        val property = kClass.memberProperties.find { it.name == conditionField }

        val field: KProperty1<out Any, *>? = targetObject::class.declaredMemberProperties.find { it.name == conditionField } // 읽고자 하는 필드명
        val fieldValue = field?.call(targetObject) as? String

        println("=========================jjjjjjjjjjjjjjj")
        targetObject::class.declaredMemberProperties.map { println(it.name) }
        println(fieldValue)
        println("=========================jjjjjjjjjjjjjjj")

        return fieldValue
//

//
//        return property?.getter?.call(targetObject) as? String
    }
}
