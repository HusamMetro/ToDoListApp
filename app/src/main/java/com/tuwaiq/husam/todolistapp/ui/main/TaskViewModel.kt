package com.tuwaiq.husam.todolistapp.ui.main

import androidx.lifecycle.ViewModel
import com.tuwaiq.husam.todolistapp.data.Repo
import com.tuwaiq.husam.todolistapp.data.Task

class TaskViewModel : ViewModel() {
    fun insertTasktoList(task: Task) {
        Repo.insertTasktoList(task)
    }
}