package org.example.auth.application.auth.service.role

import org.example.auth.domain.auth.dao.AuthoritiesJpaDao
import org.example.auth.domain.auth.dao.RoleHierarchyJpaDao
import org.example.auth.domain.auth.dao.UserAuthoritiesJpaDao
import org.example.auth.domain.auth.entity.UserDetail
import org.example.auth.domain.auth.entity.auth.Authorities
import org.example.auth.domain.auth.entity.auth.UserAuthorities
import org.springframework.stereotype.Service

@Service
class AuthorityServiceImpl(
    private val authoritiesJpaDao: AuthoritiesJpaDao,
    private val roleHierarchyJpaDao: RoleHierarchyJpaDao,
//    private val roleHierarchy : RoleHierarchy,
    private val userAuthoritiesJpaDao: UserAuthoritiesJpaDao,
) : AuthorityService {
    override fun getAuthorityList(): List<Authorities> {
        return authoritiesJpaDao.findAll()
    }

    override fun delAuthority(authority: String) {
        userAuthoritiesJpaDao.findByAuthority(authority).forEach {
            userAuthoritiesJpaDao.delete(it)
        }

        authoritiesJpaDao.deleteById(authority)
    }

    override fun addUserAuthority(
        userDetail: UserDetail,
        authority: String,
    ): UserDetail {
        // 없으면 추가하자..
        if (!authoritiesJpaDao.findById(authority).isPresent) {
            authoritiesJpaDao.save(Authorities(authority))
        }

        val userAuthority = UserAuthorities(authority = authority).apply { this.userDetail = userDetail }
        userDetail.userAuthorities.add(userAuthority) // -> userAuthority 비영속객체
        userAuthoritiesJpaDao.save(userAuthority) // -> userAuthority 영속객체화
        return userDetail
    }

    override fun removeUserAuthority(
        userDetail: UserDetail,
        authority: String,
    ) {
        // 없으면 추가하자..
        if (!authoritiesJpaDao.findById(authority).isPresent) {
            authoritiesJpaDao.save(Authorities(authority))
        }

        val userAuthority = UserAuthorities(authority = authority).apply { this.userDetail = userDetail }
        userDetail.userAuthorities.add(userAuthority) // -> userAuthority 비영속객체
        userAuthoritiesJpaDao.save(userAuthority) // -> userAuthority 영속객체화
    }

    override fun delUserAuthority(userAuthoritiesSeq: Long) {
        val userAuthority = userAuthoritiesJpaDao.findById(userAuthoritiesSeq).orElseThrow { Exception("") }
        val target = userAuthority.userDetail.userAuthorities.find { it.authoritySeq == userAuthoritiesSeq }
        if (target != null) {
            userAuthority.userDetail.userAuthorities.remove(target)
            userAuthoritiesJpaDao.delete(target)
        }
    }
}
