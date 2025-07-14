package com.reyaz.islamiccalendar.ui.screen.calendar

import com.reyaz.islamiccalendar.domain.model.CompleteCalendar

data class CalendarUiState(
    val isLoading : Boolean = true,
    val error : String? = null,
    val month : Int? = null,
    val year : Int? = null,
    val selectedIndex : Int? = null,
    val calendarDays : CompleteCalendar? = null
)