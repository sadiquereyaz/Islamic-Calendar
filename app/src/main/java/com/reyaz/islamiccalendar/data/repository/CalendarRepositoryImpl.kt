package com.reyaz.islamiccalendar.data.repository

import android.content.Context
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
import com.reyaz.islamiccalendar.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private const val TAG = "CALENDAR_REPOSITORY_IMPL"

class CalendarRepositoryImpl(
    private val apiService: AlAdhanApiService,
    private val calendarDao: CalendarDao,
    private val appContext: Context
) : CalendarRepository {

    override fun observeCalendar(month: Int?, year: Int?): Flow<Result<CompleteCalendar>> = flow {
        try {
            if (!NetworkUtils.isInternetAvailable(appContext)) {
                emit(Result.failure(Exception("No internet connection")))
                return@flow
            }
            var targetMonth = month
            var targetYear = year
            coroutineScope {
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
                    Log.d(TAG, "Month: $targetMonth, Year: $targetYear")
                }
                Log.d(TAG, "Out from coroutine")

                // current calendar fetching
                getHijriCalendarWithGeorgian(targetMonth!!, targetYear!!)
            }
            if (targetMonth == null || targetYear == null) throw IllegalStateException("Null month or year")
            // Fetch and cache leading, current, trailing months in parallel
            val leadingMonthYear = adjustMonthYear(targetMonth!! - 1, targetYear!!)
            val trailingMonthYear = adjustMonthYear(targetMonth!! + 1, targetYear!!)

            coroutineScope {
                val fetchResults = listOf(
                    async {
                        getHijriCalendarWithGeorgian(
                            leadingMonthYear.first,
                            leadingMonthYear.second
                        )
                    },

                    async {
                        getHijriCalendarWithGeorgian(
                            trailingMonthYear.first,
                            trailingMonthYear.second
                        )
                    }
                ).map { it.await() }

                if (fetchResults.any { it.isFailure }) {
                    emit(Result.failure(Exception("Calendar fetch failed")))
                    return@coroutineScope
                }
            }

            val localCurrentDates = calendarDao.getMonthWithDates(targetMonth!!, targetYear!!)
            val localLeadingDates =
                calendarDao.getMonthWithDates(leadingMonthYear.first, leadingMonthYear.second)
            val localTrailingDates =
                calendarDao.getMonthWithDates(trailingMonthYear.first, trailingMonthYear.second)

            // Generate 5-row calendar grid starting Monday
            val calendarGrid = generateCalendarGrid(
                currentCal = localCurrentDates,
                leadingCal = localLeadingDates,
                trailingCal = localTrailingDates
            )
            val currentHijriMonthDayIndex: Int? = getTodaysIndex( calendarGrid)

            Log.d(TAG, "Current Hijri Month Day Index: $currentHijriMonthDayIndex")

            emit(
                Result.success(
                    CompleteCalendar(
                        hijriMonth = targetMonth!!,
                        hijriMonthName = localCurrentDates.month.monthName,
//                        hijriYear = localCurrentDates.month.year,
                        hijriYear = targetYear!!,
                        dateList = calendarGrid,
                        currentHijriMonthDayIndex = currentHijriMonthDayIndex
                    )
                )
            )

        } catch (e: Exception) {
            Log.e(TAG, "observeCalendar error", e)
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    private fun getTodaysIndex(
        calendarGrid: List<CalDate>
    ): Int? {

        val calendar = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        // all three are of gregorian type
        val sysDayOfMonth = calendar.dayOfMonth
        val sysMonthName = calendar.month.name
        val sysYear = calendar.year
        val currentHijriMonthDayIndex: Int? = calendarGrid.indexOfFirst { calDate ->
//            Log.d(TAG, "${calDate.gregorianMonthName} == ${sysMonthName} ")
            calDate.isIncluded && calDate.gregorianDate == sysDayOfMonth && calDate.gregorianMonthName.equals(sysMonthName, ignoreCase = true)
                    && calDate.gregorianYear == sysYear
        }.takeIf { it != -1 }
        return currentHijriMonthDayIndex
    }

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
            it.toEntity(isIncluded = false)
        }
        result.addAll(leadingToShow)

        // Fill current month
        val currentToShow = currentCal.dates.map {
            it.toEntity(isIncluded = true)
        }
        result.addAll(currentToShow)

        // Fill trailing days
        val totalSoFar = result.size
        val remainder = totalSoFar % 7
        val trailingRequired = if (remainder == 0) 0 else (7 - remainder)

//        Log.d(TAG, "Leading: ${leadingToShow.size}, Current: ${currentToShow.size}, Result so far: $totalSoFar, TrailingRequired: $trailingRequired")

        val trailingToShow = trailingCal.dates.take(trailingRequired).map {
            it.toEntity(isIncluded = false)
        }
        result.addAll(trailingToShow)

        return result
    }

    override suspend fun getHijriCalendarWithGeorgian(
        month: Int,
        year: Int
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val isExists = calendarDao.isCalExists(month, year)

            if (isExists) {
                Log.d(TAG, "Cache Present")
                return@withContext Result.success(Unit)
            }

            Log.d(TAG, "No Cache Present")
            val calendarResult = apiService.getHijriCalendarWithGeorgian(month, year)

            if (calendarResult.isSuccessful) {
                val calendarData = calendarResult.body()?.data
                    ?: throw IllegalStateException("Null calendar data")
                val monthName = calendarData.firstOrNull()?.hijri?.month?.en
                    ?: throw IllegalStateException("Null month name")

                storeMonthWithDates(
                    hijriMonth = month,
                    hijriYear = year,
                    hijriMonthName = monthName,
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
        hijriMonth: Int,
        hijriYear: Int,
        hijriMonthName: String,
        dtoList: List<HijriCalendarWithGeorgianDto>
    ) {
        val monthId = "${hijriMonth}_${hijriYear}"
        val monthEntity = CalMonthEntity(
            id = monthId,
            month = hijriMonth,
            monthName = hijriMonthName,
            year = hijriYear
        )
        calendarDao.insertMonth(monthEntity)
        val dateEntities = dtoList.map { it.toEntity(monthId) }
        calendarDao.insertDates(dateEntities)
    }

}