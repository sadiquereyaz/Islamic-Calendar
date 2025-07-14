package com.reyaz.islamiccalendar.ui.navigation

import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable
    data object Calendar : Route()
}