package org.example.auth.infrastructure.util

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeExtension

fun String.toLocalDateTime(format: String = "yyyy-MM-dd'T'HH:mm:ss"): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(format)
    return LocalDateTime.parse(this, formatter)
}

fun String.toLocalStartDateTime(): LocalDateTime {
    val format: String = "yyyy-MM-dd'T'HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(format)
    return LocalDateTime.parse("$this'T'00:00:00", formatter)
}

fun String.toLocalEndDateTime(): LocalDateTime {
    val format: String = "yyyy-MM-dd'T'HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(format)
    return LocalDateTime.parse("$this'T'23:59:59", formatter)
}

// LocalDate에 일수를 더하는 확장 함수
fun LocalDate.addDays(days: Long): LocalDate = this.plusDays(days)

// LocalDate에 일수를 빼는 확장 함수
fun LocalDate.subtractDays(days: Long): LocalDate = this.minusDays(days)

fun LocalDateTime.formatToString(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val formatter = DateTimeFormatter.ofPattern(format)
    return this.format(formatter)
}

// 두 LocalDateTime 간의 차이를 초 단위로 계산하는 확장 함수
fun LocalDateTime.secondsUntil(other: LocalDateTime): Long {
    return Duration.between(this, other).seconds
}

// 두 LocalDateTime 간의 차이를 분 단위로 계산하는 확장 함수
fun LocalDateTime.minutesUntil(other: LocalDateTime): Long {
    return Duration.between(this, other).toMinutes()
}
