package com.tuwaiq.husam.todolistapp.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.husam.todolistapp.data.AppRepo
import com.tuwaiq.husam.todolistapp.data.model.Task
import kotlinx.coroutines.launch

class TaskViewModel(context: Application) : AndroidViewModel(context) {
    private val repo: AppRepo = AppRepo(context)

    fun insertTaskToList(task: Task) = viewModelScope.launch {
        repo.insertTaskToList(task)
    }

    fun deleteTaskFromList(task: Task) = viewModelScope.launch {
        repo.deleteTaskFromList(task)
    }

    fun updateTaskOnList(task: Task) = viewModelScope.launch {
        repo.updateTaskOnList(task)
    }
}