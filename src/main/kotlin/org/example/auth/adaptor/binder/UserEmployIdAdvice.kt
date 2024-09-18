package org.example.auth.adaptor.binder

// import com.example.ddd.adaptor.auth.UserContext
import org.example.auth.domain.auth.Account
import org.springframework.core.MethodParameter
import org.springframework.http.HttpInputMessage
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice
import java.lang.reflect.Type

@RestControllerAdvice
class UserEmployIdAdvice : RequestBodyAdvice {
    override fun supports(
        methodParameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>,
    ): Boolean {
        return methodParameter.parameterType.interfaces.contains(UserIdSetAdvice::class.java)
    }

    override fun beforeBodyRead(
        inputMessage: HttpInputMessage,
        parameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>,
    ): HttpInputMessage {
        return inputMessage
    }

    override fun afterBodyRead(
        body: Any,
        inputMessage: HttpInputMessage,
        parameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>,
    ): Any {
        try {
            val temp = body as UserIdSetAdvice
            val account = SecurityContextHolder.getContext().authentication.principal as Account
            temp.userSeq = account.userSeq
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return body
    }

    override fun handleEmptyBody(
        body: Any?,
        inputMessage: HttpInputMessage,
        parameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>,
    ): Any? {
        return body
    }
}
