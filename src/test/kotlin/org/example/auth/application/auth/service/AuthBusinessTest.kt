package org.example.auth.application.auth.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.example.auth.adaptor.auth.dto.AccountDto
import org.example.auth.adaptor.auth.dto.AccountProfileDto
import org.example.auth.config.RedisTestConfig
import org.example.auth.config.UserRegProcess
import org.example.auth.domain.auth.dao.UserDaoImpl
import org.example.auth.infrastructure.cipher.AesCipherImpl
import org.example.auth.infrastructure.config.CacheConfig
import org.example.auth.infrastructure.config.NotifyServiceConfig
import org.example.auth.infrastructure.config.QuerydslConfig
import org.example.auth.infrastructure.util.modelMapTo
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import io.kotest.extensions.spring.SpringExtension


@DataJpaTest
@Import(UserServiceImpl::class, UserDaoImpl::class, AesCipherImpl::class,
    RedisTestConfig::class, UserRegProcess::class, QuerydslConfig::class,
    NotifyServiceConfig::class, CacheConfig::class,)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 DB로 테스트
@DisplayName("회원 관련 서비스 테스트")
class AuthBusinessTest : BehaviorSpec() {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder


    init {
        extension(SpringExtension)
        Given("test3 유져가 등록되고") {
            userService.registUser(
                AccountDto(userId = "test3", userName = "테스터2", userPwd = "test"),
                accountProfileDto = AccountProfileDto(email = "test@test.com").modelMapTo(),
            )
            When("test3 유저 ID 중복체크시") {
                val actual = userService.isNotDuplicateUserId("test3")
                Then ("중복이다.") {
                    actual shouldBe false
                }
            }
        }
    }

}