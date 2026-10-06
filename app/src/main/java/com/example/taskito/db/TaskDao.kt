package com.example.taskito.db

import androidx.room.*
import com.example.taskito.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)
    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM TASKS ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE taskColor = :color")
    fun getTasksByColor(color: String): Flow<List<Task>>
}