package com.tuwaiq.husam.todolistapp.ui.main

import androidx.lifecycle.ViewModel
import com.tuwaiq.husam.todolistapp.data.Repo
import com.tuwaiq.husam.todolistapp.data.Task

class MainViewModel : ViewModel() {

    // TODO: Implement the ViewModel
    fun fillTaskListWithData() {
        Repo.fillTaskListWithData()
    }


    fun getTaskList(): List<Task> {
        return Repo.getTaskList()
    }
}