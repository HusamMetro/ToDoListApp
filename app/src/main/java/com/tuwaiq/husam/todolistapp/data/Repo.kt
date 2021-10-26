package com.tuwaiq.husam.todolistapp.data

import com.tuwaiq.husam.todolistapp.data.model.Task

object Repo {

    private val taskList: MutableList<Task> = mutableListOf()
    fun getTaskList(): List<Task> {
        return taskList
    }

    fun fillTaskListWithData() {
        /*if (taskList.isEmpty()) {
            for (index in 0..15) {
                taskList += Task("Task Title = $index ")
            }
        }*/
    }

    fun insertTaskToList(task: Task) {
        taskList += task
    }

    fun deleteTaskFromList(task: Task) {
        taskList -= task
    }

    fun updateTaskOnList(task: Task, updatedTask: Task) {
        taskList -= task
        taskList += updatedTask
    }

}