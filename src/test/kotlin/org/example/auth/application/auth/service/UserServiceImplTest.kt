package org.example.auth.application.auth.service

import org.example.auth.adaptor.auth.dto.AccountDto
import org.example.auth.adaptor.auth.dto.AccountProfileDto
import org.example.auth.adaptor.auth.dto.UserSearchParamReq
import org.example.auth.config.RedisTestConfig
import org.example.auth.config.UserRegProcess
import org.example.auth.domain.auth.dao.UserDaoImpl
import org.example.auth.domain.auth.Account
import org.example.auth.infrastructure.cipher.AesCipherImpl
import org.example.auth.infrastructure.config.CacheConfig
import org.example.auth.infrastructure.config.NotifyServiceConfig
import org.example.auth.infrastructure.config.QuerydslConfig
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertNotNull

@DataJpaTest
@Import(UserServiceImpl::class, UserDaoImpl::class, AesCipherImpl::class,
        RedisTestConfig::class, UserRegProcess::class, QuerydslConfig::class,
        NotifyServiceConfig::class, CacheConfig::class,)
//@ContextConfiguration(initializers = [MysqlTestContainer::class])
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 DB로 테스트
//@Rollback(false)
// 하기 위한 설정
@DisplayName("회원 서비스 테스트")
class UserServiceImplTest {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun registUser() {
        userService.registUser(
            AccountDto(userId = "test2", userName = "테스터2", userPwd = "test"),
            accountProfileDto = AccountProfileDto(email = "ej.lee@naver.com" ),
        )
    }

    @Test
    fun changePwd() {
        val account = Account("", "", "")
        account.userSeq = 2
        account.userPwd = "test123"
        userService.changePwd(account)
    }

    @Test
    fun getUser() {
        val result = userService.userSearch(UserSearchParamReq(), PageRequest.of(0, 5))
        assertEquals(result.totalElements, 2)
        assertNotNull(userService.getUser(2), "회원정보 존재")
    }

    @Test
    fun modifyUserInfo() {
//        언제 쓰지? 회원기본정보 변경이...
//        userService.modifyUserInfo()
    }

    @Test
    fun isNotDuplicateUserId() {
        val check = userService.isNotDuplicateUserId("test")
        assertEquals(check, true)

        val check2 = userService.isNotDuplicateUserId("test2")
        assertEquals(check2, false)
    }

    @Test
    fun modifyMarketingInfo() {
    }

    @Test
    fun passwordChangeDay() {
    }

    @Test
    fun quizList() {
    }

    @Test
    fun askDropOut() {
    }

    @Test
    fun cancelDropOut() {
    }

    @Test
    fun doDropOut() {
    }

    @Test
    fun loadUserByUsername() {
        val account = userDetailsService.loadUserByUsername("test")
        println(account)
    }
}
