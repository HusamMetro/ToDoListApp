package com.tuwaiq.husam.todolistapp.ui.main

import androidx.lifecycle.ViewModel
import com.tuwaiq.husam.todolistapp.data.Repo
import com.tuwaiq.husam.todolistapp.data.model.Task

class TaskViewModel : ViewModel() {
    private val repo: Repo = Repo

    fun insertTaskToList(task: Task) {
        repo.insertTaskToList(task)
    }

    fun deleteTaskFromList(task: Task) {
        repo.deleteTaskFromList(task)
    }

    fun updateTaskOnList(
        position: Int,
        title: String,
        description: String,
        desCompleted: String = ""
    ) {
        repo.updateTaskOnList(position, title, description, desCompleted)
    }
}