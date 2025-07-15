package com.reyaz.islamiccalendar.ui.screen.calendar.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reyaz.islamiccalendar.ui.screen.calendar.CalendarUiState

@Composable
fun CalendarContent(
    modifier: Modifier = Modifier,
    uiState: CalendarUiState.Success,
    onCellClick: (Int) -> Unit
) {
    var selectedIndex : Int? by remember { mutableStateOf(uiState.selectedIndex) }
    CustomCardContainer(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val dayList = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
        // week days row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // week days row
            dayList.forEach { day ->
                Text(
                    text = day,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .weight(1f)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = CircleShape
                        )
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
            uiState.data.dateList.let { it ->
                itemsIndexed(it) { index, calCell ->
                    Card(
                        modifier = Modifier
                            .height(80.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (index == uiState.data.currentHijriMonthDayIndex && calCell.isIncluded) MaterialTheme.colorScheme.primary else if (calCell.isIncluded) Color.Unspecified else Color.Transparent,
                        ),
                        shape = RoundedCornerShape(6.dp),
                        border = if (index == selectedIndex) BorderStroke(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        else BorderStroke(width = 0.dp, color = Color.Transparent),
                        onClick = {
                            selectedIndex = index
                            onCellClick(index)
                        }
                    ) {
                        Text(
                            text = "${calCell.hijriDate}",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                            //                            .weight(1f)
                            color = if (calCell.isIncluded) Color.Unspecified else MaterialTheme.colorScheme.outline
                        )

                        Text(
                            text = "(${calCell.gregorianDate})",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(start = 4.dp)
                            //                                .weight(1f)
                            ,
                            color = MaterialTheme.colorScheme.outline,
                            lineHeight = 12.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier
                                .align(Alignment.End)
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            repeat(calCell.holidays.size) { index ->
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
//                                        .padding(horizontal = 8.dp)
                                        .clip(CircleShape)
                                        .background(
                                            getPaletteColorByIndex(index)
                                        )
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}

