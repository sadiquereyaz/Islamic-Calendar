package com.reyaz.islamiccalendar.ui.screen.calendar

import com.reyaz.islamiccalendar.domain.model.CompleteCalendar
/*

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
*/


sealed class CalendarUiState {
    data object Loading : CalendarUiState()

    data class Success(
        val data: CompleteCalendar,
        val selectedIndex: Int = /*data.currentHijriMonthDayIndex*/12
    ) : CalendarUiState() {

        val currCalHijriMonthYear: String
            get() = "${data.hijriMonthName} ${data.hijriYear}"

        val selectedGregorianDate: String
            get() {
                val selectedGregorianCellData = data.dateList[selectedIndex]
                return "${selectedGregorianCellData.gregorianDate} ${selectedGregorianCellData.gregorianMonthName} ${selectedGregorianCellData.gregorianYear}"
            }

        val selectedHolidayList : List<String> = data.dateList[selectedIndex].holidays
    }

    data class Error(val message: String) : CalendarUiState()
}

