package org.example.auth.config

import org.assertj.core.api.AssertionsForClassTypes.assertThat
// import org.example.auth.adaptor.controller.api.auth.UserController
import org.example.auth.domain.auth.dao.UserJpaDao
import org.example.auth.domain.auth.dao.UserLoginHistoryDao
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithMockUser

@WebMvcTest
class SecurityConfigTest2() {
    @Mock
    lateinit var userDao: UserJpaDao

    @Mock
    lateinit var userLoginHistoryDao: UserLoginHistoryDao

//    @Mock
//    lateinit var userService : UserService
//
//    @Mock
//    lateinit var twoFactAuthService : TwoFactAuthService
//
//    @Mock
//    lateinit var notifyServiceFactory : NotifyServiceFactory

    @InjectMocks
    lateinit var successHandler: CustomSuccessHandler

//    @InjectMocks
//    lateinit var userController: UserController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    @WithMockUser(username = "test", password = "test")
    fun testSuccessHandler() {
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()

        val authentication = SecurityContextHolder.getContext().authentication

        println(authentication)

        successHandler.onAuthenticationSuccess(request, response, authentication)

        // IP 주소나 리다이렉트 URL 등 체크
        assertThat(response.redirectedUrl).isEqualTo("/home")
    }
}
