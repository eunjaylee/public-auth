package org.example.auth.domain.auth.dao

import org.example.auth.domain.auth.entity.UserDetail
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.*

interface UserJpaDao : JpaRepository<UserDetail, Long> {
    @EntityGraph(attributePaths = ["userAuthorities"])
    fun findByUserId(userId: String): Optional<UserDetail>

    // 연령별 사용자 수 조회
    @Query("SELECT i.birthDate, COUNT(u) FROM UserDetail u inner join UserPersonalInfo i on u.userSeq = i.userSeq GROUP BY i.birthDate")
    fun countUsersByAge(): List<Array<Any>>

    // 성별 사용자 수 조회
    @Query("SELECT i.gender, COUNT(u) FROM UserDetail u inner join UserPersonalInfo i on u.userSeq = i.userSeq GROUP BY i.gender")
    fun countUsersByUserPersonalInfoGender(): List<Array<Any>>

    // 지역별 사용자 수 조회
    @Query(
        "SELECT i.userAdditionalInfo.zipcode, COUNT(u) FROM UserDetail u inner join UserPersonalInfo i on u.userSeq = i.userSeq GROUP BY i.userAdditionalInfo.zipcode",
    )
    fun countUsersByRegion(): List<Array<Any>>

    // 가입일 기준 특정 기간 내 신규 가입자 수
    fun countByCreateAtBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): Long

    fun findByRecommendCode(code: String): Slice<UserDetail>
}
