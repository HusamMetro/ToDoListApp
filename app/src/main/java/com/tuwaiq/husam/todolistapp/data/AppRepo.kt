package com.tuwaiq.husam.todolistapp.data

import android.content.Context
import com.tuwaiq.husam.todolistapp.data.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepo(context: Context) {
    private val localAppDatabase: AppDatabase = AppDatabase.getAppDatabase(context)!!

    suspend fun getTaskList(): List<Task> = withContext(Dispatchers.IO) {
        localAppDatabase.taskDao.getAllTasks()
    }

    suspend fun insertTaskToList(task: Task) = withContext(Dispatchers.IO) {
        localAppDatabase.taskDao.insert(task)
    }

    suspend fun deleteTaskFromList(task: Task) = withContext(Dispatchers.IO) {
        localAppDatabase.taskDao.delete(task)
    }

    suspend fun updateTaskOnList(task: Task) = withContext(Dispatchers.IO) {
        localAppDatabase.taskDao.update(task)
    }
}