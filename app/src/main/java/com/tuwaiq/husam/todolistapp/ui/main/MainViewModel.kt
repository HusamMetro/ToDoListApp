package com.tuwaiq.husam.todolistapp.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuwaiq.husam.todolistapp.data.AppRepo
import com.tuwaiq.husam.todolistapp.data.model.Task
import kotlinx.coroutines.launch

class MainViewModel(context: Application) : AndroidViewModel(context) {
    private val repo: AppRepo = AppRepo(context)

    fun getTaskList(): MutableLiveData<List<Task>> {
        val tasks = MutableLiveData<List<Task>>()
        viewModelScope.launch {
            tasks.postValue(repo.getTaskList())
        }
        return tasks
    }
    fun updateTaskOnList(task: Task) = viewModelScope.launch {
        repo.updateTaskOnList(task)
    }
    // ---------------------------- for sorting ----------------------------
    /*val sortedList = Repo.getTaskList().sortedByDescending {
           it.TaskTitle
       }
       return sortedList*/
    //----------------------------------------------------------------------
}