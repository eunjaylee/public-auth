package org.example.auth.application.auth.service

import org.example.auth.config.RedisTestConfig
import org.example.auth.config.UserRegProcess
import org.example.auth.domain.auth.dao.UserDaoImpl
import org.example.auth.infrastructure.cipher.AesCipherImpl
import org.example.auth.infrastructure.config.CacheConfig
import org.example.auth.infrastructure.config.NotifyServiceConfig
import org.example.auth.infrastructure.config.QuerydslConfig
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@Import(UserServiceImpl::class, UserDaoImpl::class, AesCipherImpl::class,
    RedisTestConfig::class, UserRegProcess::class, QuerydslConfig::class,
    NotifyServiceConfig::class, CacheConfig::class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 DB로 테스트 없으면 h2db
@DisplayName("회원 탈퇴 테스트")
class DropUserServiceImplTest {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun testPwd() {
        val encodedPassword = passwordEncoder.encode("12345")

        println(encodedPassword)
    }

    @Test
    fun registUser() {
        TODO()
//        val userDetail = userService.registUser(UserDetail(userId ="test"+ Random(10).nextInt(), userPwd = "test"))

//        println(userDetail.toStringByReflection())

//        println(userService.getUser(userDetail.userSeq).toStringByReflection())
    }

    @Test
    fun getUser() {
        TODO()
    }

    @Test
    fun modifyUserInfo() {
        TODO()
    }

    @Test
    fun askDropOut() {
        TODO()
    }

    @Test
    fun doDropOut() {
        TODO()
    }
}
