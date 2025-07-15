package com.reyaz.islamiccalendar.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reyaz.islamiccalendar.ui.screen.calendar.CalendarScreen
import com.reyaz.islamiccalendar.ui.screen.calendar.CalendarViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun IslamicCalendarApp(
    navController: NavHostController = rememberNavController()
) {
        NavHost(
            navController = navController,
            startDestination = Route.Calendar,
        ) {
            composable<Route.Calendar> {
                val viewmodel : CalendarViewModel = koinViewModel()
                val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
                CalendarScreen(
                    modifier = Modifier.fillMaxSize(),
                    uiState = uiState,
                    onNavigateClick = { changeBy: Int->
                        viewmodel.changeMonth(changeBy)
                    },
                    onCellClick = {
                        viewmodel.onCellClick(it)
                    },
                    retry = {
                        viewmodel.retry()
                    }
                )
            }
        }

}