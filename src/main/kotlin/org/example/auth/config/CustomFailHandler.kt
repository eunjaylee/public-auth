package org.example.auth.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.auth.domain.auth.dao.UserDao
import org.example.auth.domain.auth.entity.UserDetail
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class CustomFailHandler(
    val userDao: UserDao,
) : SimpleUrlAuthenticationFailureHandler() {
    companion object {
        const val MAX_FAILED_ATTEMPTS: Int = 3
    }

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?,
    ) {
        exception?.printStackTrace()

        val username = request!!.getParameter("username")
        val user: UserDetail? = userDao.findByUserId(username).orElse(null)

        if (user != null) {
            if (exception is BadCredentialsException) {
                if (user.failedLoginAttempts < MAX_FAILED_ATTEMPTS - 1) {
                    user.failedLoginAttempts += 1
                    userDao.save(user)
                } else {
                    user.accountLocked = true
                    userDao.save(user)
                }
            }
        }

        super.onAuthenticationFailure(request, response, exception)
    }
}
