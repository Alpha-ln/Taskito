package com.example.taskito.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

private val HighColor = Color(0xFFE24B4A)
private val MediumColor = Color(0xFFEF9F27)
private val LowColor = Color(0xFF5DCAA5)


@Composable
fun PriorityBreakdownBar(
    state: InsightsUiState,
    modifier: Modifier = Modifier
) {
    val total = state.totalPriorityCount.coerceAtLeast(1)
    val highFraction = state.highPriorityCount.toFloat() / total
    val mediumFraction = state.mediumPriorityCount.toFloat() / total
    val lowFraction = state.lowPriorityCount.toFloat() / total

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(6.dp))
        ) {
            if (highFraction > 0f) Box(Modifier.weight(highFraction).fillMaxHeight().background(HighColor))
            if (mediumFraction > 0f) Box(Modifier.weight(mediumFraction).fillMaxHeight().background(MediumColor))
            if (lowFraction > 0f) Box(Modifier.weight(lowFraction).fillMaxHeight().background(LowColor))
        }

        Spacer(Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("High ${(highFraction * 100).roundToInt()}%", fontSize = 11.sp, color = Color.Gray)
            Text("Medium ${(mediumFraction * 100).roundToInt()}%", fontSize = 11.sp, color = Color.Gray)
            Text("Low ${(lowFraction * 100).roundToInt()}%", fontSize = 11.sp, color = Color.Gray)

        }

    }
}