package com.reyaz.islamiccalendar.ui.screen.calendar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.reyaz.islamiccalendar.ui.components.IslamicCalendarTopAppBar
import com.reyaz.islamiccalendar.ui.screen.calendar.components.CalendarContent
import com.reyaz.islamiccalendar.ui.screen.calendar.components.HolidayContent

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    onCellClick: (Int) -> Unit,
    uiState: CalendarUiState,
    onNavigateClick: (Int) -> Unit
) {
    val lastTitle = rememberSaveable { mutableStateOf("Islamic Calendar") }
    val lastSubtitle = rememberSaveable { mutableStateOf<String?>(null) }

    if (uiState is CalendarUiState.Success) {
        lastTitle.value = uiState.currCalHijriMonthYear
        lastSubtitle.value = uiState.selectedGregorianDate
    }

    val title = lastTitle.value
    val subtitle = lastSubtitle.value

    Scaffold(
        modifier = modifier.pointerInput(Unit) {
            detectHorizontalDragGestures { change, dragAmount ->
                change.consume()
                if (dragAmount > 50) {
                    // Swiped right, go to previous month
                    onNavigateClick(-1)
                } else if (dragAmount < -50) {
                    // Swiped left, go to next month
                    onNavigateClick(1)
                }
            }
        },
        topBar = {
            IslamicCalendarTopAppBar(
                title = title,
                subtitle = subtitle,
                isEnabled = uiState is CalendarUiState.Success,
                changeMonth = onNavigateClick
            )
        },
    ) { innerPadding ->
        when (uiState) {
            is CalendarUiState.Loading -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    CircularProgressIndicator(modifier = Modifier
                        .size(32.dp)
                        .padding())
                    Text(text = "Loading...", modifier = Modifier.padding(top = 16.dp))
                }
            }

            is CalendarUiState.Success -> {
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
                    uiState.selectedHolidayList?.isNotEmpty()?.let {
                        AnimatedVisibility(it) {
                            HolidayContent(
                                modifier = Modifier,
                                holidayList = uiState.selectedHolidayList
                            )
                        }
                    }
                }
            }

            is CalendarUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    Text(
                        text = uiState.message,
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
