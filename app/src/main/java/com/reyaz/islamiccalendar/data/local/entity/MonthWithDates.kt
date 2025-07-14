package com.reyaz.islamiccalendar.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MonthWithDates(
    @Embedded val month: CalMonthEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "monthId"
    )
    val dates: List<CalDateEntity>
)
