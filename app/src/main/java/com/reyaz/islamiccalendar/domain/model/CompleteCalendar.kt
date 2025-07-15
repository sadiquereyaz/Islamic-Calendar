package com.reyaz.islamiccalendar.domain.model

data class CompleteCalendar(
    val hijriMonth: Int,
    val hijriMonthName: String,
    val hijriYear: Int,
    val currentHijriMonthDayIndex: Int?,
    val dateList: List<CalDate>
)