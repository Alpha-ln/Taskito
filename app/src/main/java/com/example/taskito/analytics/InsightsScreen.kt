package com.example.taskito.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskito.R

@Composable
fun InsightsScreen(
    state: InsightsUiState,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
            .padding(top = 16.dp)
    ) {
        if (onBackClick != null) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(32.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = "Insights",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        CompletionSection(state)

        Spacer(Modifier.height(32.dp))
        MetricCardsRow(state)

        Spacer(Modifier.height(24.dp))
        Text("Completed per day", fontSize = 13.sp, color = Color.Gray)
        Spacer(Modifier.height(8.dp))
        WeeklyBarChart(state.weeklyCompletions)

        Spacer(Modifier.height(24.dp))
        PriorityBreakdownBar(state)
    }
}

@Composable
private fun MetricCardsRow(state: InsightsUiState) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        MetricCard(
            label = "Current streak",
            value = "${state.currentStreak} days",
            backgroundColor = Color(0xFFFAEEDA),
            textColor = Color(0xFF412402),
            labelColor = Color(0xFF854F0B),
            modifier = Modifier.weight(1f)
        )
        MetricCard(
            label = "Overdue",
            value = "${state.overdueCount}",
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            textColor = MaterialTheme.colorScheme.onSurface,
            labelColor = Color.Gray,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun MetricCard(
    label: String,
    value: String,
    backgroundColor: Color,
    textColor: Color,
    labelColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(label, fontSize = 13.sp, color = labelColor)
        Spacer(Modifier.height(4.dp))
        Text(value, fontSize = 22.sp, fontWeight = FontWeight.Medium, color = textColor)
    }
}

