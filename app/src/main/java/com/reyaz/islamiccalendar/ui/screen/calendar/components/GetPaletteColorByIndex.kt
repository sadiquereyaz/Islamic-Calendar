package com.reyaz.islamiccalendar.ui.screen.calendar.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getPaletteColorByIndex(index: Int): Color {
    val isLight = /*!isSystemInDarkTheme()*/true

    return when (index % 4) {
        0 -> Color(0xFFB500D3)
        1 -> Color(0xFF00AADC)
        2 -> Color(0xFF00A66F)
        3 -> Color(0xFFE91E63)
        else -> {Color.Gray}
    }
}