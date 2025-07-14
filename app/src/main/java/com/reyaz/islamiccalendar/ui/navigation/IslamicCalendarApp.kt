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
import androidx.navigation.navigation
import com.reyaz.islamiccalendar.ui.components.IslamicCalendarTopAppBar
import com.reyaz.islamiccalendar.ui.screen.calendar.CalendarScreen

@Composable
fun IslamicCalendarApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = { IslamicCalendarTopAppBar(title = "Islamic Calendar") },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Calendar,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Routes.Calendar> {
                CalendarScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}