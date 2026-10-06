package com.example.taskito.model

import com.example.taskito.db.TaskDao

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks = taskDao.getAllTasks()

    suspend fun insert(task: Task) = taskDao.insertTask(task)
    suspend fun update(task: Task) = taskDao.updateTask(task)
    suspend fun delete(task: Task) = taskDao.deleteTask(task)
}