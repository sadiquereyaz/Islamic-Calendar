package com.reyaz.islamiccalendar.ui.screen.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.reyaz.islamiccalendar.ui.components.IslamicCalendarTopAppBar
import com.reyaz.islamiccalendar.ui.screen.calendar.components.CalendarContent
import com.reyaz.islamiccalendar.ui.screen.calendar.components.HolidayContent

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    uiState: CalendarUiState
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            val title = if (uiState.month != null && uiState.year != null) "${uiState.month} ${uiState.year}"  else "Islamic Calendar"
            IslamicCalendarTopAppBar(
                title = title,
                changeMonth = {

                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CalendarContent(
                modifier = Modifier,
                uiState = uiState
            )
            HolidayContent()
        }
    }
}