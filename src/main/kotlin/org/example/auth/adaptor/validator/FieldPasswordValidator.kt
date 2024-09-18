package org.example.auth.adaptor.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class FieldPasswordValidator : ConstraintValidator<FieldValidPassword, String> {
    private lateinit var fieldValidPassword: FieldValidPassword

    override fun initialize(constraintAnnotation: FieldValidPassword) {
        this.fieldValidPassword = constraintAnnotation
    }

    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext,
    ): Boolean {
        if (value == null) return false

        if (value.length < fieldValidPassword.minLength || value.length > fieldValidPassword.maxLength) {
            return false
        }

        if (fieldValidPassword.hasUpperCase && !value.any { it.isUpperCase() }) {
            return false
        }

        if (fieldValidPassword.hasLowerCase && !value.any { it.isLowerCase() }) {
            return false
        }

        if (fieldValidPassword.hasDigit && !value.any { it.isDigit() }) {
            return false
        }

        if (fieldValidPassword.hasSpecialChar && !value.any { !it.isLetterOrDigit() }) {
            return false
        }

        return true
    }
}
