package com.example.taskito.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.taskito.analytics.InsightsScreen
import com.example.taskito.analytics.InsightsUiState
import com.example.taskito.model.Task
import com.example.taskito.viewmodel.TaskViewModel
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

class AnalyticsFragment : Fragment() {
    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val tasks by viewModel.tasks.collectAsState(initial = emptyList())
                val uiState = remember(tasks) { calculateInsightsUiState(tasks) }
                MaterialTheme {
                    InsightsScreen(
                        state = uiState,
                        onBackClick = { findNavController().navigateUp() }
                    )
                }
            }
        }
    }
}

fun calculateInsightsUiState(tasks: List<Task>): InsightsUiState {
    val completedCount = tasks.count { it.isCompleted }
    val pendingCount = tasks.count { !it.isCompleted }
    val now = System.currentTimeMillis()
    val overdueCount = tasks.count { !it.isCompleted && it.dueDate < now }

    val highPriorityCount = tasks.count {
        it.taskColor.equals("High", ignoreCase = true) || it.taskColor.equals("Red", ignoreCase = true)
    }
    val mediumPriorityCount = tasks.count {
        it.taskColor.equals("Medium", ignoreCase = true) || it.taskColor.equals("Yellow", ignoreCase = true)
    }
    val lowPriorityCount = tasks.count {
        it.taskColor.equals("Low", ignoreCase = true) || it.taskColor.equals("Green", ignoreCase = true)
    }

    val zoneId = ZoneId.systemDefault()
    val today = LocalDate.now(zoneId)

    val dailyHeatmap = mutableMapOf<LocalDate, Int>()
    tasks.filter { it.isCompleted && it.completedAt != null }.forEach { task ->
        val date = Instant.ofEpochMilli(task.completedAt!!).atZone(zoneId).toLocalDate()
        dailyHeatmap[date] = (dailyHeatmap[date] ?: 0) + 1
    }

    val startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val weeklyCompletions = (0..6).map { dayOffset ->
        val date = startOfWeek.plusDays(dayOffset.toLong())
        dailyHeatmap[date] ?: 0
    }

    var currentStreak = 0
    var checkDate = today
    if ((dailyHeatmap[checkDate] ?: 0) == 0) {
        checkDate = today.minusDays(1)
    }
    while ((dailyHeatmap[checkDate] ?: 0) > 0) {
        currentStreak++
        checkDate = checkDate.minusDays(1)
    }

    return InsightsUiState(
        completedCount = completedCount,
        pendingCount = pendingCount,
        currentStreak = currentStreak,
        overdueCount = overdueCount,
        weeklyCompletions = weeklyCompletions,
        highPriorityCount = highPriorityCount,
        mediumPriorityCount = mediumPriorityCount,
        lowPriorityCount = lowPriorityCount,
        dailyHeatmap = dailyHeatmap
    )
}
