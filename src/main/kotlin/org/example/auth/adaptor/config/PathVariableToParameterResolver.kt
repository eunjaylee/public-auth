package org.example.auth.adaptor.config

// @Component
// class PathVariableToParameterResolver : HandlerMethodArgumentResolver {
//    override fun supportsParameter(parameter: MethodParameter): Boolean {
//        // @RequestParam, @PathVariable 등의 어노테이션이 없고 단순 타입의 매개변수를 처리
//        // PathVariable query_param 전환하는 목적을 가짐
//        return (!parameter.hasParameterAnnotation(PathVariable::class.java)
//                && !parameter.hasParameterAnnotation(RequestParam::class.java)
//                && isSimpleValueType(parameter.parameterType))
//    }
// //
// //    override fun supportsParameter(parameter: MethodParameter): Boolean {
// //        // 모든 기본 타입 매개변수 지원
// //        return (!parameter.hasParameterAnnotation(PathVariable::class.java)
// //                && !parameter.hasParameterAnnotation(ModelAttribute::class.java)
// //                && parameter.parameterType.isAssignableFrom(String::class.java))
// //    }
// //
//
//    @Throws(Exception::class)
//    override fun resolveArgument(
//        parameter: MethodParameter, mavContainer: ModelAndViewContainer?,
//        webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?
//    ): Any? {
//        val paramName = parameter.parameterName
//        val pathVariables = webRequest.getAttribute(
//            HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, NativeWebRequest.SCOPE_REQUEST
//        ) as Map<String, String>
//
//        if (pathVariables != null && pathVariables.containsKey(paramName)) {
//            return pathVariables[paramName]
//        }
//
//        return webRequest.getParameter(paramName) // fallback to request parameter or return null
//    }
//
//    private fun isSimpleValueType(parameterType: Class<*>): Boolean {
//        return parameterType.isPrimitive || parameterType == String::class.java ||
//                Number::class.java.isAssignableFrom(parameterType)
//    }
// }
