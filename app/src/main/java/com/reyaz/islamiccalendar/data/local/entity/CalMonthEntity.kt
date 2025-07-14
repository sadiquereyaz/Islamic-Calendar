package com.reyaz.islamiccalendar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cal_month")
data class CalMonthEntity(
    @PrimaryKey val id: String, // "${month}_${year}", e.g., "7_2025"       // todo: delete commment
    val month: Int,
    val monthName: String,
    val year: Int,
)
