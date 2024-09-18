package org.example.auth.adaptor.binder

// import org.springframework.core.DefaultParameterNameDiscoverer
// import org.springframework.core.ParameterNameDiscoverer
// import org.springframework.http.HttpInputMessage
// import org.springframework.http.converter.HttpMessageConverter
// import org.springframework.web.bind.annotation.PathVariable
// import org.springframework.web.bind.annotation.RestControllerAdvice
// import org.springframework.web.context.request.RequestAttributes
// import org.springframework.web.context.request.RequestContextHolder
// import org.springframework.web.servlet.HandlerMapping
// import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter

import org.springframework.core.DefaultParameterNameDiscoverer
import org.springframework.core.MethodParameter
import org.springframework.core.ParameterNameDiscoverer
import org.springframework.http.HttpInputMessage
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.util.ReflectionUtils
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.servlet.HandlerMapping
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Parameter
import java.lang.reflect.Type

@RestControllerAdvice
class PathVariableRequestBodyBinder : RequestBodyAdviceAdapter() {
    override fun afterBodyRead(
        body: Any?,
        inputMessage: HttpInputMessage?,
        methodParameter: MethodParameter,
        targetType: Type?,
        converterType: Class<out HttpMessageConverter<*>?>?,
    ): Any {
        val `object` = super.afterBodyRead(body!!, inputMessage!!, methodParameter, targetType!!, converterType!!)
        val method: Method? = methodParameter.method

        val requestAttributes = RequestContextHolder.currentRequestAttributes()
        val pathVariables =
            requestAttributes.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
                RequestAttributes.SCOPE_REQUEST,
            ) as MutableMap<*, *>

        if (method == null || pathVariables.isEmpty()) {
            return `object`
        }

//        val paramMap = HashMap<String, Any>()

        val clazz: Class<*> = `object`.javaClass
        val parameterNames: Array<out String>? = parameterNameDiscoverer.getParameterNames(method)
        if (parameterNames != null && parameterNames.isNotEmpty()) {
            val parameters: Array<Parameter> = method.parameters
            for (index in parameterNames.indices) {
                val parameterName = parameterNames[index]
                val parameter: Parameter = parameters[index]
                // NOTE: Set PathVariable into RequestBody.
                if (parameter.isAnnotationPresent(PathVariable::class.java) && pathVariables.containsKey(parameterName)) {
                    try {
                        val field: Field = clazz.getDeclaredField(parameterName)
                        ReflectionUtils.makeAccessible(field)
                        val o: Any? = ReflectionUtils.getField(field, `object`)
                        if (o != null) {
                            ReflectionUtils.setField(field, `object`, convertValue(pathVariables[parameterName], field.type))
                        }
                    } catch (ignored: NoSuchFieldException) {
                        // ignored
//                        ignored.printStackTrace()
                    } catch (e: Exception) {
                        // ignored
//                        e.printStackTrace()
                    }
                }
            }
        }

        return `object`
    }

    fun convertValue(
        value: Any?,
        targetType: Class<*>,
    ): Any? {
        return when (targetType) {
            Int::class.java -> (value as? String)?.toInt()
            Long::class.java -> (value as? String)?.toLong()
            Double::class.java -> (value as? String)?.toDouble()
            Boolean::class.java -> (value as? String)?.toBoolean()
            else -> value // Use the value as-is for other types
        }
    }

    override fun supports(
        methodParameter: MethodParameter,
        targetType: Type?,
        converterType: Class<out HttpMessageConverter<*>?>?,
    ): Boolean {
        val method: Method? = methodParameter.method
        if (method != null) {
            val parameters: Array<Parameter> = method.parameters
            for (parameter in parameters) {
                if (parameter.isAnnotationPresent(PathVariable::class.java)) {
                    return true
                }
            }
        }
        return false
    }

    companion object {
        private val parameterNameDiscoverer: ParameterNameDiscoverer = DefaultParameterNameDiscoverer()
    }
}
