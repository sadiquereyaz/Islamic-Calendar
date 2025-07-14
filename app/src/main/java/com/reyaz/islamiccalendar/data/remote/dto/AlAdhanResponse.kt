package com.reyaz.islamiccalendar.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AlAdhanResponse<T>(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("status")
    val status: String? = null
)