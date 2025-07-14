package com.reyaz.islamiccalendar.data.mapper

import com.reyaz.islamiccalendar.data.local.entity.CalDateEntity
import com.reyaz.islamiccalendar.data.remote.dto.HijriCalendarWithGeorgianDto

fun HijriCalendarWithGeorgianDto.toEntity(monthId: String): CalDateEntity {
    return CalDateEntity(
        gregorianDate = this.gregorian?.date.orEmpty(),
        hijriDate = this.hijri?.date.orEmpty(),
//        gregorianDay = this.gregorian?.day.orEmpty(),
//        hijriDay = this.hijri?.day.orEmpty(),
        weekday = this.gregorian?.weekday?.en.orEmpty(),
//        hijriMonthName = this.hijri?.month?.en.orEmpty(),
        gregorianMonthName = this.gregorian?.month?.en.orEmpty(),
//        isHoliday = !this.hijri?.holidays.isNullOrEmpty(),
        holidays = this.hijri?.holidays ?: emptyList(),
        monthId = monthId
    )
}
