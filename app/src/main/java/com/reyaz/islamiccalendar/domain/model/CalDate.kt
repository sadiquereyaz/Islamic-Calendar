package com.reyaz.islamiccalendar.domain.model

data class CalDate(
    val weekday: String,
    val hijriDate: Int,
    val gregorianDate: Int,
    val gregorianMonthName: String,
    val gregorianYear: Int,
    val holidays: List<String>
)


