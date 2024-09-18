package org.example.auth.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class OAuthRoleFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication

        if (authentication?.isAuthenticated == true &&
            authentication.authorities.contains(SimpleGrantedAuthority("ROLE_OAUTH"))
        ) {
            val requestUri = request.requestURI
            val allowedUris = listOf("/login", "/signup")

            if (!allowedUris.any { requestUri.startsWith(it) }) {
                response.sendRedirect("/login")
                return
            }
        }

        filterChain.doFilter(request, response)
    }
}
