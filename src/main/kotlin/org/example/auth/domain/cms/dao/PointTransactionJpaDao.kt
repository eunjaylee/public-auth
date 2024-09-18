package org.example.auth.domain.cms.dao

import org.example.auth.domain.cms.entity.PointTransaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PointTransactionJpaDao : JpaRepository<PointTransaction, Long> {
    fun findByUserSeq(userSeq: Long): PointTransaction
}
