package org.example.auth.domain.auth.dao

import org.example.auth.domain.auth.entity.auth.UserAuthorities
import org.springframework.data.jpa.repository.JpaRepository

interface UserAuthoritiesJpaDao : JpaRepository<UserAuthorities, Long> {
    fun findByAuthority(authorities: String): List<UserAuthorities>
}
