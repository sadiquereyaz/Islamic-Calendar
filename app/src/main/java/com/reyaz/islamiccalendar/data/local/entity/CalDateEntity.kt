package com.reyaz.islamiccalendar.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cal_date",
    foreignKeys = [
        ForeignKey(
            entity = CalMonthEntity::class,
            parentColumns = ["id"],
            childColumns = ["monthId"],
        )
    ],
    indices = [Index(value = ["monthId"])]
)
data class CalDateEntity(
    @PrimaryKey val gregorianDate: String, // "YYYY-MM-DD"
    val hijriDate: String,
//    val gregorianDay: String,
//    val hijriDay: String,
    val weekday: String,
//    val hijriMonthName: String,
    val gregorianMonthName: String,
//    val isHoliday: Boolean = false,
    val holidays: List<String>,
    val monthId: String // FK referencing CalMonthEntity.id
)
