package org.example.auth.application.auth.service

import com.querydsl.core.Tuple
import java.time.LocalDateTime

interface UserStatisticsService {
    // 연령별 사용자 수 조회
    fun countUsersByAge(): List<Array<Any>>

    // 성별 사용자 수 조회
    fun countUsersByGender(): List<Array<Any>>

    // 지역별 사용자 수 조회
    fun countUsersByRegion(): List<Array<Any>>

    // 가입일 기준 특정 기간 내 신규 가입자 수
    fun getNewUsers(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): Long

    // 마지막 로그인 날짜 기준 활성 사용자 수 - 중복제거?
    fun getActiveUsers(date: LocalDateTime): Long

    // 마지막 로그인 날짜 기준 특정 기간 내 활동한 사용자 수 - 중복제거?
    fun getUsersActiveInPeriod(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): Long

    /**
     * 총 회원 수
     */
    fun getTotalUsers(): Long

    /**
     * 나이대 별 회원 수
     */
    fun getUserAgeGroup(): List<Tuple>

    fun dashboard(): Map<String, String>
}
