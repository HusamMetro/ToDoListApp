package com.tuwaiq.husam.todolistapp.ui.main

import androidx.lifecycle.ViewModel
import com.tuwaiq.husam.todolistapp.data.Repo
import com.tuwaiq.husam.todolistapp.data.model.Task

class MainViewModel : ViewModel() {

    fun fillTaskListWithData() {
        Repo.fillTaskListWithData()
    }


    fun getTaskList(): List<Task> {
        return Repo.getTaskList()
        /*val sortedList = Repo.getTaskList().sortedByDescending {
            it.TaskTitle
        }
        return sortedList*/
    }
}