package org.example.auth.config

import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.util.AntPathMatcher
import java.util.*
import java.util.function.Supplier

class CustomAuthorizationManager : AuthorizationManager<RequestAuthorizationContext> {
    private val pathMatcher = AntPathMatcher()

    override fun check(
        authentication: Supplier<Authentication>,
        context: RequestAuthorizationContext,
    ): AuthorizationDecision {
        val auth = authentication.get()
        val method = context.request.method
        val requiredAuthority = getRequiredAuthorityForMethod(method)

        val requestPath = context.request.requestURI // Get the path from the request

        val hasPermission =
            auth.authorities.stream()
                .map<String> { obj: GrantedAuthority -> obj.authority }
                .anyMatch { authority: String ->
                    val roleName =
                        authority.replace("ROLE_", "")
                            .lowercase(Locale.getDefault()) // Extract and normalize role
                    val requiredAuthority = getRequiredAuthorityForMethod(method)
                    pathMatcher.match("/$roleName/**", requestPath) && authority == requiredAuthority
                }

        return AuthorizationDecision(hasPermission)
    }

    private fun getRequiredAuthorityForMethod(method: String): String {
        return when (method) {
            "GET" -> "READ"
            "POST" -> "WRITE"
            "PUT" -> "EDIT"
            "DELETE" -> "DELETE"
            else -> "ROLE_USER" // Default role for methods not specifically handled
        }
    }
}
