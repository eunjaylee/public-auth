package org.example.auth.domain.auth.dao

import org.example.auth.domain.auth.entity.UserLoginHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*

interface UserLoginHistoryDao : JpaRepository<UserLoginHistory, Long> {
    fun findFirstByUserSeqOrderByUserLoginHistorySeqDesc(userSeq: Long): Optional<UserLoginHistory>

//    fun findByUserSeqAndCurrentYn(userSeq : Long, currentYn : String = "N") : Optional<UserLoginHistory>

    fun findByUserSeq(
        userSeq: Long,
        page: Pageable,
    ): Page<UserLoginHistory>

    // 마지막 로그인 날짜 기준 활성 사용자 수
    fun countByLoginDtimeAfter(date: LocalDateTime): Long

    // 마지막 로그인 날짜 기준 특정 기간 내 활동한 사용자 수
    fun countByLoginDtimeBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): Long
}
