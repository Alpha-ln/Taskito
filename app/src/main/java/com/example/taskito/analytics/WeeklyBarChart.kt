package com.example.taskito.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DayLabels = listOf("M", "T", "W", "T", "F", "S", "S")
private val BarColorDefault = Color(0xFFAFA9EC)
private val BarColorHighlight = Color(0xFF7F77DD)

@Composable
fun WeeklyBarChart(counts: List<Int>, modifier: Modifier = Modifier) {
    require(counts.size == 7) { "WeeklyBarChart expects exactly 7 values (Mon..Sun)" }

    val maxCount = (counts.maxOrNull() ?: 0).coerceAtLeast(1)
    val maxBarHeight = 70.dp

    Row(
        modifier = modifier.height(maxBarHeight + 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        counts.forEachIndexed { index, count ->
            val isHighlighted = count == maxCount && count > 0
            val barHeight = maxBarHeight * (count.toFloat() / maxCount).coerceIn(0.06f, 1f)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(barHeight)
                        .clip(RoundedCornerShape(4.dp))
                        .background(if (isHighlighted) BarColorHighlight else BarColorDefault)
                )
                Spacer(Modifier.height(4.dp))
                Text(DayLabels[index], fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}
