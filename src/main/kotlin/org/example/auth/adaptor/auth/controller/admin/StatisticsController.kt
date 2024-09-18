package org.example.auth.adaptor.auth.controller.admin

import com.querydsl.core.Tuple
import io.swagger.v3.oas.annotations.Operation
import org.example.auth.application.auth.service.UserStatisticsService
import org.example.auth.infrastructure.util.toLocalEndDateTime
import org.example.auth.infrastructure.util.toLocalStartDateTime
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/api/v1/user/statistics")
class StatisticsController(
    private val userStatisticsService: UserStatisticsService,
) {
    @Operation(summary = "dashboard : 요약")
    @GetMapping("/", "/dashboard")
    fun dashboard(): Map<String, String> {
        // TODO factory로 만들고 merge를 하도록 하면 깔끔하지 않을까?
        return userStatisticsService.dashboard()
    }

    @Operation(summary = "총 회원 수 조회")
    @GetMapping("/total-users")
    fun getTotalUsers(): Long {
        return userStatisticsService.getTotalUsers()
    }

    @Operation(summary = "활성 사용자 수 조회")
    @GetMapping("/active-users")
    fun getActiveUsers(
        @RequestParam afterDate: String,
    ): Long {
        return userStatisticsService.getActiveUsers(afterDate.toLocalStartDateTime())
    }

    @Operation(summary = "신규 가입자 수 조회")
    @GetMapping("/new-users")
    fun getNewUsers(
        @RequestParam startDate: String,
        @RequestParam endDate: String,
    ): Long {
        return userStatisticsService.getNewUsers(startDate.toLocalStartDateTime(), endDate.toLocalEndDateTime())
    }

    @Operation(summary = "특정 기간 내 활동한 사용자 수 조회")
    @GetMapping("/users-active-in-period")
    fun getUsersActiveInPeriod(
        @RequestParam startDate: String,
        @RequestParam endDate: String,
    ): Long {
        return userStatisticsService.getUsersActiveInPeriod(startDate.toLocalStartDateTime(), endDate.toLocalEndDateTime())
    }

    @Operation(summary = "나이대별 회원수")
    @GetMapping("/users-age")
    fun getUsersAgeGroup(): List<Tuple> {
        return userStatisticsService.getUserAgeGroup()
    }
}
