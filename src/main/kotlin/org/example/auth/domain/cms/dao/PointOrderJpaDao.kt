package org.example.auth.domain.cms.dao

import org.example.auth.domain.cms.entity.PointOrder
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface PointOrderJpaDao : JpaRepository<PointOrder, Long> {
    fun findByUserSeq(
        userSeq: Long,
        pageable: Pageable,
    ): Page<PointOrder>
}
