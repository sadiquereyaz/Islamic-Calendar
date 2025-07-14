package com.reyaz.islamiccalendar.data.repository

import android.util.Log
import com.reyaz.islamiccalendar.data.local.dao.CalendarDao
import com.reyaz.islamiccalendar.data.local.entity.CalMonthEntity
import com.reyaz.islamiccalendar.data.local.entity.MonthWithDates
import com.reyaz.islamiccalendar.data.mapper.toEntity
import com.reyaz.islamiccalendar.data.remote.api.AlAdhanApiService
import com.reyaz.islamiccalendar.data.remote.dto.HijriCalendarWithGeorgianDto
import com.reyaz.islamiccalendar.domain.model.CalDate
import com.reyaz.islamiccalendar.domain.model.CompleteCalendar
import com.reyaz.islamiccalendar.domain.repository.CalendarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

private const val TAG = "CALENDAR_REPOSITORY_IMPL"

class CalendarRepositoryImpl(
    private val apiService: AlAdhanApiService,
    private val calendarDao: CalendarDao
) : CalendarRepository {

    override fun observeCalendar(month: Int?, year: Int?): Flow<Result<CompleteCalendar>> = flow {
        try {
            // Fetch and cache leading, current, trailing months in parallel
            val leadingMonthYear =
                if (month != null && year != null) adjustMonthYear(month - 1, year) else null
            val trailingMonthYear =
                if (month != null && year != null) adjustMonthYear(month + 1, year) else null

            coroutineScope {
                val fetchResults = listOf(
                    async {
                        getHijriCalendarWithGeorgian(
                            leadingMonthYear?.first,
                            leadingMonthYear?.second
                        )
                    },
                    async { getHijriCalendarWithGeorgian(month, year) },
                    async {
                        getHijriCalendarWithGeorgian(
                            trailingMonthYear?.first,
                            trailingMonthYear?.second
                        )
                    }
                ).map { it.await() }

                if (fetchResults.any { it.isFailure }) {
                    emit(Result.failure(Exception("Calendar fetch failed")))
                    return@coroutineScope
                }
            }

            val localCurrentDates = calendarDao.getMonthWithDates(month!!, year!!)
            val localLeadingDates =
                calendarDao.getMonthWithDates(leadingMonthYear?.first!!, leadingMonthYear.second)
            val localTrailingDates =
                calendarDao.getMonthWithDates(trailingMonthYear?.first!!, trailingMonthYear.second)

            // Generate 5-row calendar grid starting Monday
            val calendarGrid = generateCalendarGrid(
                localCurrentDates,
                localLeadingDates,
                localTrailingDates
            )

            emit(
                Result.success(
                    CompleteCalendar(
                        hijriMonth = month,
                        hijriMonthName = localCurrentDates.month.monthName,
                        hijriYear = localCurrentDates.month.year,
                        dateList = calendarGrid
                    )
                )
            )

        } catch (e: Exception) {
            Log.e(TAG, "observeCalendar error", e)
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    private fun adjustMonthYear(month: Int, year: Int): Pair<Int, Int> {
        return when {
            month <= 0 -> Pair(12, year - 1)
            month > 12 -> Pair(1, year + 1)
            else -> Pair(month, year)
        }
    }

    private fun generateCalendarGrid(
        leadingCal: MonthWithDates,
        currentCal: MonthWithDates,
        trailingCal: MonthWithDates
    ): List<CalDate> {

        val firstDayOfWeek = currentCal.dates.first().weekday
        val dayOfWeekMap = mapOf(
            "Sunday" to 0, "Monday" to 1, "Tuesday" to 2, "Wednesday" to 3,
            "Thursday" to 4, "Friday" to 5, "Saturday" to 6
        )
        val startOffset = (dayOfWeekMap[firstDayOfWeek] ?: 0) - 1
        val leadingRequired = if (startOffset < 0) 6 else startOffset

        val result = mutableListOf<CalDate>()

        // Fill leading days
        val leadingToShow = leadingCal.dates.takeLast(leadingRequired).map {
            it.toEntity()
        }
        result.addAll(leadingToShow)

        // Fill current month
        val currentToShow = currentCal.dates.map {
            it.toEntity()
        }
        result.addAll(currentToShow)

        // Fill trailing days
        val trailingRequired = 35 - result.size
        val trailingToShow = trailingCal.dates.take(trailingRequired).map {
            it.toEntity()
        }
        result.addAll(trailingToShow)

        return result
    }

    override suspend fun getHijriCalendarWithGeorgian(
        month: Int?,
        year: Int?
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            var targetMonth = month
            var targetYear = year

            if (targetMonth == null || targetYear == null) {
                val currentMonthDeferred = async { apiService.getCurrentIslamicMonth() }
                val currentYearDeferred = async { apiService.getCurrentIslamicYear() }

                val monthResponse = currentMonthDeferred.await()
                val yearResponse = currentYearDeferred.await()

                if (monthResponse.isSuccessful && yearResponse.isSuccessful) {
                    targetMonth = monthResponse.body()?.data
                        ?: throw IllegalStateException("Null current month")
                    targetYear = yearResponse.body()?.data
                        ?: throw IllegalStateException("Null current year")
                } else {
                    throw IllegalStateException("Month/year fetch failed: month=${monthResponse.code()}, year=${yearResponse.code()}")
                }
            }

            val isExists = calendarDao.isCalExists(targetMonth, targetYear)

            if (isExists) {
                Log.d(TAG, "Cache Present")
                return@withContext Result.success(Unit)
            }

            Log.d(TAG, "No Cache Present")
            val calendarResult = apiService.getHijriCalendarWithGeorgian(targetMonth, targetYear)

            if (calendarResult.isSuccessful) {
                val calendarData = calendarResult.body()?.data
                    ?: throw IllegalStateException("Null calendar data")
                val monthName = calendarData.firstOrNull()?.hijri?.month?.en
                    ?: throw IllegalStateException("Null month name")

                storeMonthWithDates(
                    month = targetMonth,
                    year = targetYear,
                    monthName = monthName,
                    dtoList = calendarData
                )
                return@withContext Result.success(Unit)
            } else {
                throw IllegalStateException("Calendar fetch failed: ${calendarResult.code()}")
            }

        } catch (e: Exception) {
            Log.e(TAG, "getHijriCalendarWithGeorgian error", e)
            Result.failure(e)
        }
    }

    private suspend fun storeMonthWithDates(
        month: Int,
        year: Int,
        monthName: String,
        dtoList: List<HijriCalendarWithGeorgianDto>
    ) {
        val monthId = "${month}_${year}"
        val monthEntity = CalMonthEntity(
            id = monthId,
            month = month,
            monthName = monthName,
            year = year
        )
        calendarDao.insertMonth(monthEntity)
        val dateEntities = dtoList.map { it.toEntity(monthId) }
        calendarDao.insertDates(dateEntities)
    }

}