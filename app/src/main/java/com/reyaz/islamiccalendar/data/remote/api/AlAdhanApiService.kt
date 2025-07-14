package com.reyaz.islamiccalendar.data.remote.api

import com.reyaz.islamiccalendar.data.remote.dto.AlAdhanResponse
import com.reyaz.islamiccalendar.data.remote.dto.HijriCalendarWithGeorgianDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlAdhanApiService {
    @GET("currentIslamicMonth")
    suspend fun getCurrentIslamicMonth(): Response<AlAdhanResponse<Int>>

    @GET("currentIslamicYear")
    suspend fun getCurrentIslamicYear(): Response<AlAdhanResponse<Int>>

    @GET("hToGCalendar/{month}/{year}")
    suspend fun getHijriCalendarWithGeorgian(@Path("month") month: Int, @Path("year") year: Int): Response<AlAdhanResponse<List<HijriCalendarWithGeorgianDto>>>
}