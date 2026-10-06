package com.example.taskito.model

import java.util.UUID
import androidx.room.*
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val dueDate: Long, // Timestamp
    val taskColor: String, // "Green", "Yellow", "Red"
    val isCompleted: Boolean = false,
    val createdAt: Long,
    val completedAt: Long? = null
)
