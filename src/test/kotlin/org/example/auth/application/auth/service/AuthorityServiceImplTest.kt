package org.example.auth.application.auth.service

import org.example.auth.application.auth.service.role.AuthorityServiceImpl
import org.example.auth.config.MysqlTestContainer
import org.example.auth.domain.auth.dao.UserAuthoritiesJpaDao
import org.example.auth.domain.auth.dao.UserDao
import org.example.auth.domain.auth.dao.UserDaoImpl
import org.example.auth.infrastructure.cipher.AesCipherImpl
import org.example.auth.infrastructure.config.QuerydslConfig
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@Import(AuthorityServiceImpl::class, UserDaoImpl::class, AesCipherImpl::class, QuerydslConfig::class)
//@ContextConfiguration(initializers = [MysqlTestContainer::class])
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE) // 실제 DB로 테스트 하기 위한 설정
//@Rollback(false)
@DisplayName("회원 권한 설정")
class AuthorityServiceImplTest {
    @Autowired
    lateinit var authorityService: AuthorityServiceImpl

    @Autowired
    lateinit var userDao: UserDao

    @Autowired
    lateinit var userAuthoritiesJpaDao: UserAuthoritiesJpaDao

//
//    @Test
//    fun createAuthGroup() {
//        val test  = authorityService.createAuthGroup("test")
//
//        println(test.toStringByReflection())
//
//    }
//
//    @Test
//    fun deleteAuthGroup() {
//        authorityService.deleteAuthGroup(4)
//    }
//
//    @Test
//    fun getAuthGroupList() {
//        authorityService.getAuthGroupList().map{println(it.toStringByReflection())}
//    }
//
//    @Test
//    fun createAuthGroupAuthority() {
//        val test = authorityService.getAuthGroupList()[3]
//
//        println("===========================")
//        println(test.toStringByReflection())
//
//        val authSet = setOf(
//            AuthGroupAuthorities(authorities = "test_read", group = test),
//                            AuthGroupAuthorities(authorities = "test_write", group = test),
//                            AuthGroupAuthorities(authorities = "test_delete", group = test)
//        )
//
//        test.groupAuthorities = authSet
//
//        println("===========================")
//        authorityService.createAuthGroupAuthority(test)
//
//    }

    @Test
    fun deleteAuthGroupAuthority() {
    }
//
//    @Test
//    fun addUserAuthGroup() {
//        val userDetail = userDao.findById(1).orElseThrow {Exception("")}
// //        val authGroup = authorityService.getAuthGroupList()[3]
//        val test = authorityService.addUserAuthGroup(userDetail, 4)
//        println("=================")
//        println(test.toStringByReflection())
//        println("=================")
//    }

    @Test
    fun addUserAuthority() {
        val userDetail = userDao.findById(1).orElseThrow { Exception("") }
        authorityService.addUserAuthority(userDetail, "READ_TEST")
    }

    @Test
    fun delUserAuthority() {
        authorityService.delUserAuthority(1)
    }
}
