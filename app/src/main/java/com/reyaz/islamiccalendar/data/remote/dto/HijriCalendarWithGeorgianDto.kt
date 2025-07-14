package com.reyaz.islamiccalendar.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HijriCalendarWithGeorgianDto(
    @SerializedName("gregorian")
    val gregorian: Gregorian? = Gregorian(),
    @SerializedName("hijri")
    val hijri: Hijri? = Hijri()
)

data class Gregorian(
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("day")
    val day: String? = null,
    @SerializedName("designation")
    val designation: Designation? = null,
    @SerializedName("format")
    val format: String? = null,
    @SerializedName("lunarSighting")
    val lunarSighting: Boolean? = null,
    @SerializedName("month")
    val month: Month? = null,
    @SerializedName("weekday")
    val weekday: Weekday? = null,
    @SerializedName("year")
    val year: String? = null
)

data class Hijri(
    @SerializedName("adjustedHolidays")
    val adjustedHolidays: List<Any?>? = listOf(),
    @SerializedName("date")
    val date: String? = "",
    @SerializedName("day")
    val day: String? = "",
    @SerializedName("designation")
    val designation: Designation? = Designation(),
    @SerializedName("format")
    val format: String? = "",
    @SerializedName("holidays")
    val holidays: List<String>? = listOf(),
    @SerializedName("method")
    val method: String? = "",
    @SerializedName("month")
    val month: MonthX? = MonthX(),
    @SerializedName("weekday")
    val weekday: WeekdayX? = WeekdayX(),
    @SerializedName("year")
    val year: String? = ""
)

data class Designation(
    @SerializedName("abbreviated")
    val abbreviated: String? = null,
    @SerializedName("expanded")
    val expanded: String? = null
)

data class Month(
    @SerializedName("en")
    val en: String? = null,
    @SerializedName("number")
    val number: Int? = null
)

data class Weekday(
    @SerializedName("en")
    val en: String? = null
)

data class MonthX(
    @SerializedName("ar")
    val ar: String? = null,
    @SerializedName("days")
    val days: Int? = null,
    @SerializedName("en")
    val en: String? = null,
    @SerializedName("number")
    val number: Int? = null
)

data class WeekdayX(
    @SerializedName("ar")
    val ar: String? = null,
    @SerializedName("en")
    val en: String? = null
)