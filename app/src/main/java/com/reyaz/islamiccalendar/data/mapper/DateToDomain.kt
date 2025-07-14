package com.reyaz.islamiccalendar.data.mapper

import com.reyaz.islamiccalendar.data.local.entity.CalDateEntity
import com.reyaz.islamiccalendar.data.remote.dto.HijriCalendarWithGeorgianDto
import com.reyaz.islamiccalendar.domain.model.CalDate

fun CalDateEntity.toEntity(): CalDate {
    return CalDate(
        hijriDate = this.hijriDate.take(2).toInt(),
        weekday = this.weekday,
        holidays = this.holidays,
        gregorianDate = this.gregorianDate.take(2).toInt(),
        gregorianMonthName = this.gregorianMonthName,
        gregorianYear = this.gregorianDate.takeLast(4).toInt()
    )
}
