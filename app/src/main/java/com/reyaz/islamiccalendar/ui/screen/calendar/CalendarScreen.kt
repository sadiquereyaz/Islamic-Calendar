package com.reyaz.islamiccalendar.ui.screen.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaz.islamiccalendar.ui.components.IslamicCalendarTopAppBar
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier
) {
    val currentMonth : String? = "Muharram, 1447"
    Scaffold(
        topBar = { IslamicCalendarTopAppBar(
            title = currentMonth ?: "Islamic Calendar",
            changeMonth = {

            }
        ) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            CalendarContent(
                modifier = Modifier
            )
            HolidayContent()
        }
    }
}

@Composable
fun HolidayContent() {
}

@Composable
fun CalendarContent(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
    ) {
        val dayList = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            dayList.forEach { day ->
                Text(
                    text = day,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .weight(1f)
                        .background(color = MaterialTheme.colorScheme.surface, shape = CircleShape)
                        .padding(vertical = 2.dp)

                )
            }

        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {

            val today = Random.nextInt(2..30)
            items(35) { day ->
                var isSelected by rememberSaveable { mutableStateOf(false) }
                val includeInCurrentMonth = day in 2..32
                Card(
                    modifier = Modifier.height(80.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (day == today) MaterialTheme.colorScheme.primary else if (includeInCurrentMonth) Color.Unspecified else Color.Transparent,

                        ),
                    shape = RoundedCornerShape(6.dp),
                    border = if (isSelected) BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    else BorderStroke(width = 0.dp, color = Color.Transparent),
                    onClick = { isSelected = !isSelected }
                ) {
                    Text(
                        text = day.toString(),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
//                            .weight(1f)
                        ,
                        color = if (includeInCurrentMonth) Color.Unspecified else MaterialTheme.colorScheme.outline
                    )

                    Text(
                        text = "($day)",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(start = 4.dp)
//                                .weight(1f)
                        ,
                        color = MaterialTheme.colorScheme.outline,
                        lineHeight = 12.sp
                    )
                }
            }
        }
    }
}
