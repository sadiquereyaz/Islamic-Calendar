package com.reyaz.islamiccalendar.ui.screen.calendar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HolidayContent(modifier: Modifier, holidayList: List<String>) {
    CustomCardContainer(
        modifier = modifier.padding(16.dp),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ){
        holidayList.forEachIndexed {index, holiday ->
            val color by rememberUpdatedState(getPaletteColorByIndex(index))
            Card (
                modifier = Modifier.padding(4.dp).height(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = color.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(4.dp)
            ){
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(4.dp)
                            .background(color)
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp),
                        fontWeight = FontWeight.Medium,
                        text = holiday,
//                        color = color
                    )
                }
            }
        }
    }
}