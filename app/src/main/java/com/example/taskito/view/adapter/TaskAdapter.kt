package com.example.taskito.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskito.R
import com.example.taskito.databinding.SingleTaskLayoutBinding
import com.example.taskito.model.Task
import java.util.concurrent.TimeUnit

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
}

class TaskAdapter(private val onTaskCheckedChange: (Task) -> Unit) :
    ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = SingleTaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        with(holder.binding) {
            taskText.text = task.title
            taskDescription.text = task.description

            val bgTintRes = when (task.taskColor.lowercase()) {
                "high" -> R.color.task_bg_red
                "medium" -> R.color.task_bg_yellow
                else -> R.color.task_bg_green
            }

            taskCardView.setCardBackgroundColor(
                ContextCompat.getColor(root.context, bgTintRes)
            )

            val diff = task.dueDate - System.currentTimeMillis()
            val daysRemaining = TimeUnit.MILLISECONDS.toDays(diff)
            daysRemainingText.text = when {
                daysRemaining < 0 -> "Overdue"
                daysRemaining == 0L -> "Today"
                daysRemaining == 1L -> "1 day remaining"
                else -> "$daysRemaining days remaining"
            }

            taskCheckbox.setOnCheckedChangeListener(null)
            taskCheckbox.isChecked = task.isCompleted
            taskCheckbox.setOnCheckedChangeListener { _, _ ->
                onTaskCheckedChange(task)
            }
        }
    }

    class TaskViewHolder(val binding: SingleTaskLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}
