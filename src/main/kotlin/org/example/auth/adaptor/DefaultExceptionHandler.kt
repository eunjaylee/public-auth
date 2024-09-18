package org.example.auth.adaptor

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.context.request.WebRequest
import java.util.function.Consumer

@ControllerAdvice
class DefaultExceptionHandler {
    @ModelAttribute("requestUri")
    fun populateRequestUri(request: HttpServletRequest): String {
        return request.requestURI
    }

    @ExceptionHandler(Exception::class)
    fun handlerException(
        ex: Exception,
        request: WebRequest,
    ): ResponseEntity<Map<String, Any>> {
        val errorDetails: MutableMap<String, Any> = HashMap()
        errorDetails["message"] = ex.message ?: ""
        errorDetails["status"] = HttpStatus.INTERNAL_SERVER_ERROR.value()

        // trace 정보를 포함하지 않음
        // errorDetails.put("trace", ex.getStackTrace()); // 포함할 경우 사용
        return ResponseEntity<Map<String, Any>>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors: MutableMap<String, String> = HashMap()
        ex.bindingResult.allErrors.forEach(
            Consumer { error: ObjectError ->
                val fieldName = (error as FieldError).field
                val errorMessage = error.getDefaultMessage()
                errors[fieldName] = errorMessage
            },
        )
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }
}
