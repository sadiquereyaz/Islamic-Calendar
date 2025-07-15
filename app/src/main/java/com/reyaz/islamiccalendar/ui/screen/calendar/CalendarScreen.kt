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
    onCellClick : (Int)-> Unit,
    uiState: CalendarUiState
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            IslamicCalendarTopAppBar(
                title = uiState.currentHijriMonthYear,
                subtitle = uiState.selectedGregorianDate ?: "empty",
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
                uiState = uiState,
                onCellClick = onCellClick
            )
            HolidayContent()
        }
    }
}