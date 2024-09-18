package org.example.auth.domain.auth.dao

import org.example.auth.domain.auth.entity.PasswordChangeHistory
import org.springframework.data.jpa.repository.JpaRepository

interface PasswordChangeHistoryJpaDao : JpaRepository<PasswordChangeHistory, Long> {
    fun findLimited3ByUserSeqOrderByPwdChangeSeqDesc(userSeq: Long): List<PasswordChangeHistory>
}
