package com.example.domain.utils
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTimeHelper {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    // chuyển string về localDateTime
    fun parseToLocalDateTimeOrNow(dateString: String?): LocalDateTime {
        return if (dateString != null) {
            LocalDateTime.parse(dateString, formatter)
        } else {
            LocalDateTime.now()
        }
    }

    // lấy thời điểm bắt đầu và kết thúc của ngày hôm qua
    fun getYesterday(): Pair<LocalDateTime, LocalDateTime> {
        val date = LocalDate.now().minusDays(1)
        val start = date.atStartOfDay()
        val end = date.atTime(LocalTime.MAX)
        return Pair(start, end)
    }

    //lấy thời điểm bắt đầu và kết thúc của ngày hôm nay
    fun getToday(): Pair<LocalDateTime, LocalDateTime> {
        val date = LocalDate.now()
        val start = date.atStartOfDay()
        val end = date.atTime(LocalTime.MAX)
        return Pair(start, end)
    }

    //lấy thời điểm bắt đầu và kết thúc của tuần hiện tại
    fun getThisWeek(): Pair<LocalDateTime, LocalDateTime> {
        val start = LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay()
        val end = LocalDateTime.now()
        return Pair(start, end)
    }

    //lấy thời điểm bắt đầu và kết thúc của tuần trước
    fun getLastWeek(): Pair<LocalDateTime, LocalDateTime> {
        val start = LocalDate.now().with(DayOfWeek.MONDAY).minusWeeks(1).atStartOfDay()
        val end = start.plusDays(6).with(LocalTime.MAX)
        return Pair(start, end)
    }

    //lấy thời điểm bắt đầu và kết thúc của tháng hiện tại
    fun getThisMonth(): Pair<LocalDateTime, LocalDateTime> {
        val now = LocalDate.now()
        val start = now.withDayOfMonth(1).atStartOfDay()
        return Pair(start, LocalDateTime.now())
    }

    //lấy thời điểm bắt đầu và kết thúc của tháng trước
    fun getLastMonth(): Pair<LocalDateTime, LocalDateTime> {
        val lastMonth = LocalDate.now().minusMonths(1)
        val start = lastMonth.withDayOfMonth(1).atStartOfDay()
        val end = lastMonth.withDayOfMonth(lastMonth.lengthOfMonth()).atTime(LocalTime.MAX)
        return Pair(start, end)
    }


    //lấy thời điểm bắt đầu và kết thúc của năm hiện tại
    fun getThisYear(): Pair<LocalDateTime, LocalDateTime> {
        val now = LocalDate.now()
        val start = now.withDayOfYear(1).atStartOfDay()
        val end = LocalDateTime.now()
        return Pair(start, end)
    }


    //lấy thời điểm bắt đầu và kết thúc của năm trước
    fun getLastYear(): Pair<LocalDateTime, LocalDateTime> {
        val lastYear = LocalDate.now().minusYears(1)
        val start = lastYear.withDayOfYear(1).atStartOfDay()
        val end = LocalDate.of(lastYear.year, 12, 31).atTime(LocalTime.MAX)
        return Pair(start, end)
    }
}