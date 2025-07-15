package com.reyaz.islamiccalendar.ui.screen.calendar

import com.reyaz.islamiccalendar.domain.model.CompleteCalendar
sealed class CalendarUiState {
    data object Loading : CalendarUiState()

    data class Success(
        val data: CompleteCalendar,
        val selectedIndex: Int? = data.currentHijriMonthDayIndex
    ) : CalendarUiState() {

        val currCalHijriMonthYear: String
            get() = "${data.hijriMonthName} ${data.hijriYear}"

        val selectedGregorianDate: String?
            get() {
                selectedIndex?.let {
                    val selectedGregorianCellData = data.dateList[it]
                    return "${selectedGregorianCellData.gregorianDate} ${selectedGregorianCellData.gregorianMonthName} ${selectedGregorianCellData.gregorianYear}"
                }
                return null
            }

        val selectedHolidayList : List<String>? =
            selectedIndex?.let {
                data.dateList[it].holidays
            }
    }

    data class Error(val message: String) : CalendarUiState()
}

