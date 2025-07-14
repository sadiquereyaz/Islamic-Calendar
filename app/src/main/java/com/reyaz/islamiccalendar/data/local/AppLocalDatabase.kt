package com.reyaz.islamiccalendar.data.local

import android.provider.CalendarContract
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.reyaz.islamiccalendar.data.local.dao.CalendarDao
import com.reyaz.islamiccalendar.data.local.entity.CalDateEntity
import com.reyaz.islamiccalendar.data.local.entity.CalMonthEntity

@Database(
    entities = [CalMonthEntity::class, CalDateEntity::class],
    version = 4
)
@TypeConverters(Converters::class)
abstract class AppLocalDatabase : RoomDatabase() {
    abstract val calendarDao: CalendarDao

    companion object {
        const val DATABASE_NAME = "app_db"
    }
}