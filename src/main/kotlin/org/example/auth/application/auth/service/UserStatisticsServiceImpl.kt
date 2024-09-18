package org.example.auth.application.auth.service

import com.querydsl.core.Tuple
import org.example.auth.domain.auth.dao.UserDao
import org.example.auth.domain.auth.dao.UserLoginHistoryDao
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserStatisticsServiceImpl(
    private val userDao: UserDao,
    private val userLoginHistoryDao: UserLoginHistoryDao,
) : UserStatisticsService {
    override fun countUsersByAge(): List<Array<Any>> {
        return userDao.countUsersByAge()
    }

    override fun countUsersByGender(): List<Array<Any>> {
        return userDao.countUsersByUserPersonalInfoGender()
    }

    override fun countUsersByRegion(): List<Array<Any>> {
        return userDao.countUsersByRegion() // 지역이 아니라...
    }

    override fun getNewUsers(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): Long {
        return userDao.countByCreateAtBetween(startDate, endDate)
    }

    override fun getActiveUsers(date: LocalDateTime): Long {
        return userLoginHistoryDao.countByLoginDtimeAfter(date)
    }

    override fun getUsersActiveInPeriod(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): Long {
        return userLoginHistoryDao.countByLoginDtimeBetween(startDate, endDate)
    }

    override fun getTotalUsers(): Long {
        return userDao.count()
    }

    override fun getUserAgeGroup(): List<Tuple> {
        return userDao.getUserCountByAgeGroup()
    }

    override fun dashboard(): Map<String, String> {
        return mapOf()
    }
}
