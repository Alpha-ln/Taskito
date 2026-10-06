package com.example.taskito.analytics

import java.time.LocalDate

data class InsightsUiState(
    val completedCount: Int,
    val pendingCount: Int,
    val currentStreak: Int,
    val overdueCount: Int,
    val weeklyCompletions: List<Int>,
    val highPriorityCount: Int,
    val mediumPriorityCount: Int,
    val lowPriorityCount: Int,
    val dailyHeatmap: Map<LocalDate, Int>
) {
    val completionRate: Float
        get() {
            val total = completedCount + pendingCount
            return if (total == 0) 0f else completedCount.toFloat() / total
        }

    val totalPriorityCount: Int
        get() = highPriorityCount + mediumPriorityCount + lowPriorityCount
}
