package org.example.auth.domain.auth.dao

import org.example.auth.domain.auth.entity.auth.UserProvider
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserProviderJpaDao : JpaRepository<UserProvider, String> {
    fun findByUserSeq(userSeq: Long): Optional<UserProvider>
}
