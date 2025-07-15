package com.reyaz.islamiccalendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IslamicCalendarTopAppBar(
    title: String,
    changeMonth: (Int) -> Unit,
    subtitle: String?,
    showNavigationButtons: Boolean
) {
    TopAppBar(
        title = {
            Column {
                Text(text = title, fontWeight = FontWeight.Bold)
                subtitle?.let{ Text (text = it, fontSize = 16.sp)}
            }
        },
        actions = {
            if (showNavigationButtons) {
                MonthNavigatorButton(
                    changeMonth = { changeMonth(-1) },
                    icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft
                )
                MonthNavigatorButton(
                    changeMonth = { changeMonth(+1) },
                    icon = Icons.AutoMirrored.Filled.KeyboardArrowRight
                )
            }
        }
    )
}

@Composable
private fun MonthNavigatorButton(changeMonth: () -> Unit, icon: ImageVector) {
    Icon(
        modifier = Modifier
            .padding(end = 16.dp)
            .clip(CircleShape)
            .clickable { changeMonth() }
            .background(
                color = MaterialTheme.colorScheme.outlineVariant,
            )
            .padding(4.dp),
        imageVector = icon,
        contentDescription = null
    )
}