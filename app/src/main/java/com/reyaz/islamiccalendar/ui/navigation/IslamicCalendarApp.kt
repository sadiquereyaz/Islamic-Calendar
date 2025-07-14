package com.reyaz.islamiccalendar.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reyaz.islamiccalendar.ui.components.IslamicCalendarTopAppBar
import com.reyaz.islamiccalendar.ui.screen.calendar.CalendarScreen

@Composable
fun IslamicCalendarApp(
    navController: NavHostController = rememberNavController()
) {
        NavHost(
            navController = navController,
            startDestination = Route.Calendar,
        ) {
            composable<Route.Calendar> {
                CalendarScreen(
                    modifier = Modifier
                )
            }
        }

}