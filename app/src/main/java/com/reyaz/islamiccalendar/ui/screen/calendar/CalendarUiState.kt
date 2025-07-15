package com.reyaz.islamiccalendar.ui.screen.calendar

import com.reyaz.islamiccalendar.domain.model.CompleteCalendar
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class CalendarUiState(
    val calendarDays: CompleteCalendar? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val selectedIndex: Int? = null,
) {
    val currCalHijriMonth: Int? = calendarDays?.hijriMonth
    val currCalHijriYear: Int? = calendarDays?.hijriYear

    val currentHijriMonthYear: String =
        "${calendarDays?.hijriMonthName}, ${calendarDays?.hijriYear}"

    val selectedGregorianDate: String =
        if (selectedIndex == null){

            ""
        } else {

            ""
        }

}