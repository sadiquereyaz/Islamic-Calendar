package com.reyaz.islamiccalendar.domain.repository

import com.reyaz.islamiccalendar.domain.model.CompleteCalendar
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {
    fun observeCalendar(month: Int?, year: Int?): Flow<Result<CompleteCalendar>>
    suspend fun getHijriCalendarWithGeorgian(month: Int, year: Int): Result<Unit>
}