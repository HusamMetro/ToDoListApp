package com.tuwaiq.husam.todolistapp.ui.main

import androidx.lifecycle.ViewModel
import com.tuwaiq.husam.todolistapp.data.Repo
import com.tuwaiq.husam.todolistapp.data.model.Task

class TaskViewModel : ViewModel() {
    fun insertTaskToList(task: Task) {
        Repo.insertTaskToList(task)
    }

    fun deleteTaskFromList(task: Task) {
        Repo.deleteTaskFromList(task)
    }

    fun updateTaskOnList(task: Task, updateTask: Task) {
        Repo.updateTaskOnList(task, updateTask)
    }
}